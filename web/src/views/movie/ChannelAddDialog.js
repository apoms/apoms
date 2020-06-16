import React from "react";
import {withStyles} from "@material-ui/core/styles";
import {
    Button,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    Switch,
    TextField,
    Typography
} from "@material-ui/core";

import {withSnackbar} from "notistack";
import {inject, observer} from "mobx-react";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormLabel from '@material-ui/core/FormLabel';

const styles = (theme) => ({
    dialogTitle: {
        borderBottom: `1px solid ${theme.palette.divider}`,
        padding: theme.spacing(2),
        margin: 0,
    },
    dialogContent: {
        padding: theme.spacing(2),
        margin: 0,
    },
    dialogAction: {
        borderTop: `1px solid ${theme.palette.divider}`,
        padding: theme.spacing(1),
        margin: 0,
    },
    visibleContainer: {
        display: 'flex',
    },
    noVisibleContainer: {
        display: 'none',
    },
    movieDetail: {
	    marginLeft: theme.spacing(2),
	    padding: 1,
	    minWidth: '80%',
	},
});

@inject('channelStore')
@observer
class ChannelAddDialog extends React.Component {
	
    componentDidUpdate(prevProps, prevState, snapshot) {
    
        if(this.props.channelStore.isAdded) {
            this.props.enqueueSnackbar("새로운 채널 정보를 추가하였습니다", {
                variant: 'info'
            });

            this.props.channelStore.clearDialogState(false);
        }


        if(this.props.channelStore.isAddFailed) {
            this.props.enqueueSnackbar("새로운 채널 추가에 실패하였습니다", {
                variant: 'error'
            });

            this.props.channelStore.clearDialogState(true);
        }
    }

    handleCloseDialog = () => {
        this.props.channelStore.closeDialog();
    }

    handleChangeNewChannel = (event) => {
    	 const name = event.target.name;
         let value = event.target.value;
   //      alert(name+"="+value);
         this.props.channelStore.changeNewChannel(name, value);
    }

    

    handleChangeNewServerActivated = (event) => {
        const activated = event.target.checked;

        this.props.channelStore.changeNewServerActivated(activated);
    }
    
    handleChangeNewMovieActivated = (event) => {
    	const name = event.target.name;
    	const activated = event.target.checked;
        this.props.channelStore.changeNewChannelActivated(name, activated);
    }
    

    handleChangeNewServerPriority = (event) => {
        const priority = event.target.value;

        this.props.channelStore.changeNewServerPriority(priority);
    }

    handleClickOk = () => {
        this.props.channelStore.addNewChannel();
    }

    render() {
        const { classes } = this.props;
        const { isDialogOpen, isAdding, isReadyAdding, newChannel } = this.props.channelStore;



        return (
            <Dialog open={isDialogOpen}
                    onClose={this.handleCloseDialog}
                    disableBackdropClick
                    aria-labelledby="add-dialog-title" >
                <DialogTitle id="add-dialog-title" className={classes.dialogTitle}>
                    <Grid container>
                        <Grid item xs={12}>
                            <Typography variant="h6" gutterBottom>国产 채널 추가</Typography>
                        </Grid>
                    </Grid>
                </DialogTitle>

                <DialogContent className={classes.dialogContent}>
                    <Grid container spacing={1}>
                       
                    <Grid item xs={12}>
                        <TextField className={classes.movieDetail} value={newChannel.module_name}
             		   name = "module_name"
                           onChange={this.handleChangeNewChannel} label="채널제목(중)" />
    	                    <TextField className={classes.movieDetail}  value={newChannel.module_name_eng}
             		   name = "module_name_eng"
                           onChange={this.handleChangeNewChannel} label="채널제목(영)" />                   
    	                    <TextField className={classes.movieDetail}  value={newChannel.module_name_jpn}
             		   name = "module_name_jpn"
                           onChange={this.handleChangeNewChannel} label="채널제목(일)" />
                        	   </Grid>
    	                    	
                        
                        
                        <Grid item xs={12}>
                        
                        <FormLabel component="legend">모듈/채널 구분</FormLabel>
	                        <FormControlLabel
	                        value="1"
	                        name="channel_type"
	                        onChange={this.handleChangeNewChannel}	
	                        checked={newChannel.channel_type == 1}
	                        control={<Radio color="primary" />}
	                        label="모듈"
	                      />
	                      <FormControlLabel
	                        value="2"
	                        name="channel_type"
	    	                onChange={this.handleChangeNewChannel}	
	                        checked={newChannel.channel_type == 2}
	    	                control={<Radio color="primary" />}
	                        label="채널"
	                      /> 
                        </Grid>
                    
	    	                
	    	                <Grid item xs={12}>
	                        
	                        <FormLabel component="legend">노출 분류 방식</FormLabel>
		                        <FormControlLabel
		                        value="true"
		                        name="has_selection"
		                        onChange={this.handleChangeNewMovieActivated}	
		                        checked={newChannel.has_selection === false}
		                        control={<Radio color="primary" />}
		                        label="채널별"
		                      />
		                      <FormControlLabel
		                        value="false"
		                        name="has_selection"
		    	                onChange={this.handleChangeNewMovieActivated}	
		                        checked={newChannel.has_selection === 1}
		    	                control={<Radio color="primary" />}
		                        label="전체"
		                      /> 
	                        </Grid>
		    	                
		    	                
                    
                        <Grid item xs={12}>
                        
                        <FormLabel component="legend">리스트 노출타입</FormLabel>
	                        <FormControlLabel
	                        value="1"
	                        name="show_type"
	                        onChange={this.handleChangeNewChannel}	
	                        checked={newChannel.show_type == 1}
	                        control={<Radio color="primary" />}
	                        label="두개씩 노출"
	                      />
	                      <FormControlLabel
	                        value="2"
	                        name="show_type"
	    	                onChange={this.handleChangeNewChannel}	
	                        checked={newChannel.show_type == 2}
	    	                control={<Radio color="primary" />}
	                        label="한개씩 노출"
	                      /> 
                        </Grid>
	                        
	                        
                        <Grid item xs={12}>
                        
                        <Typography component="div">
	                        <FormLabel component="legend">노출 유무 </FormLabel>
	                        미노출 <FormControlLabel
	                        control={<Switch  checked={newChannel.user_yn} onChange={this.handleChangeNewMovieActivated} name="user_yn" />}
	                        label="노출"
	                      />
                        </Typography>
                    
                        </Grid>
                        <Grid item xs={12}>
                        <TextField className={classes.movieDetail}  value={newChannel.show_order}
              		   name = "show_order"
                            onChange={this.handleChangeNewChannel} label="순번" />
                        </Grid>
                       
                    </Grid>
                </DialogContent>

                <DialogActions className={classes.dialogAction}>
                    <Button color="secondary" onClick={this.handleCloseDialog}>
                        닫기
                    </Button>
                    <Button color="primary" disabled={isAdding || !isReadyAdding} onClick={this.handleClickOk}>
                        {isAdding ? <CircularProgress size={22}/> : '확인'}
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

export default withSnackbar(withStyles(styles) (ChannelAddDialog));
