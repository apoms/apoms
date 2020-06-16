import React from "react";
import {withSnackbar} from "notistack";
import {withRouter} from "react-router-dom";
import {withStyles} from "@material-ui/core/styles";
import {
    Button,
    Container,
    LinearProgress,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableFooter,
    TableHead,
    TablePagination,
    TableRow,
    Toolbar
    
} from "@material-ui/core";
import {green, red} from "@material-ui/core/colors";
import AddIcon from "@material-ui/icons/Add";
import DoneIcon from "@material-ui/icons/Done";
import ClearIcon from "@material-ui/icons/Clear";

import {inject, observer} from "mobx-react";
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import IconButton from '@material-ui/core/IconButton';
import InputBase from '@material-ui/core/InputBase';
import Divider from '@material-ui/core/Divider';
import SearchIcon from '@material-ui/icons/Search';
import TextField from '@material-ui/core/TextField';
import FormLabel from '@material-ui/core/FormLabel';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import VideoLibrary from '@material-ui/icons/VideoLibrary';
import ChannelAddDialog from "./ChannelAddDialog";
import VideoLibraryIcon from '@material-ui/icons/VideoLibrary';
import {CircularProgress, Grid} from "@material-ui/core";
import MaterialTable, {MTableToolbar} from "material-table";
import axios from "axios";
import AddRoundedIcon from '@material-ui/icons/AddRounded';
import GetAppIcon from "@material-ui/icons/GetApp";
import fileDownload from "js-file-download";
import Chip from '@material-ui/core/Chip';
import TagFacesIcon from '@material-ui/icons/TagFaces';
import ImageIcon from '@material-ui/icons/Image';
import {toJS} from "mobx";
import moment from "moment";
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import SwipeableViews from 'react-swipeable-views';
import Radio from '@material-ui/core/Radio';

const styles = theme => ({
    mainContainer: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    appBarSpacer: theme.mixins.toolbar,
    mainContent: {
        marginTop: theme.spacing(2),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    toolbar: {
        width: '100%',
    },
    grow: {
        flexGrow: 1,
    },
    delGrow: {
        flexGrow: 0,
    },
    bodyPaper: {
        width: '100%',
        marginTop: theme.spacing(1),
        overflowX: 'auto',
    },
    bodyTable: {
        minWidth: 700,
    },
    greenIcon: {
        color: green[500],
        verticalAlign: 'middle',
    },
    redIcon: {
        color: red[500],
        verticalAlign: 'middle',
    },
    loadingProgress: {
        height: 0.5,
    },
    
    formControl: {
        margin: theme.spacing(1),
        minWidth: 220,
    },
    
    switchButton: {
        marginLeft: theme.spacing(2),
        padding: 2,
    },
    
	selectEmpty: {
		marginTop: theme.spacing(2),
	},
	movieDetail: {
	    marginLeft: theme.spacing(2),
	    padding: 2,
	    minWidth: '80%',
	},
	movieDetailHalf: {
	    marginLeft: theme.spacing(2),
	    padding: 2,
	    minWidth: '40%',
	    // display: 'flex',
	},
	textField: {
	   margin: theme.spacing(2),
	   display: 'flex',
	},
	fileText: {
        flexGrow: 1,
        paddingRight: theme.spacing(2),
        textAlign: 'right',
    },
    filebox: {
        marginRight: theme.spacing(1),
        marginLeft: theme.spacing(1),
        flexGrow: 100,
    },
    
    fileboxCover: {
        marginRight: theme.spacing(1),
        marginLeft: theme.spacing(1),
        flexGrow: 98,
    },
    
    fileSelection: {
        position: 'absolute',
        width: 1,
        height: 1,
        padding: 0,
        margin: -1,
        overflow: 'hidden',
        clip: 'rect(0,0,0,0)',
        border: 0,
        borderRadius: 12,
    },
    fileButton: {
        marginLeft: theme.spacing(1),
        borderRadius: 25,
    },
    uploadButton: {
        borderRadius: 25,
    },
    
    movieDetailCover: {
	    //marginLeft: theme.spacing(2),
	    padding: 2,
	    
	    //flexGrow: 1,
	    //display: 'flex',
	    minWidth: '83%',
	},
    
    
    CProgress: {
    	// position: 'absolute',
    	// bottom: '10%',
    	left: '50%',
    	// right: '50%',
// , left: 0,
    // right: 0, bottom: 0,
    	justifyContent: 'center', 
    	alignItems: 'center'	
    },
    root: {
        display: 'flex',
        justifyContent: 'center',
        flexWrap: 'wrap',
        listStyle: 'none',
        padding: theme.spacing(0.5),
        margin: 0,
      },
      chip: {
        margin: theme.spacing(0.5),
      },
 /*     
      root: {
  	    flexGrow: 1,
  	    backgroundColor: theme.palette.background.paper,
  	  },
	*/
	
});

function TabPanel(props) {
	  const { children, value, index, ...other } = props;

	  return (
	    <div
	      role="tabpanel"
	      hidden={value !== index}
	      id={`wrapped-tabpanel-${index}`}
	      aria-labelledby={`wrapped-tab-${index}`}
	      {...other}
	    >
	      {value === index && (
	        <Box p={3}>
	          <Typography>{children}</Typography>
	        </Box>
	      )}
	    </div>
	  );
	}
/*
	TabPanel.propTypes = {
	  children: PropTypes.node,
	  index: PropTypes.any.isRequired,
	  value: PropTypes.any.isRequired,
	};
*/
	function a11yProps(index) {
	  return {
	    id: `wrapped-tab-${index}`,
	    'aria-controls': `wrapped-tabpanel-${index}`,
	  };
	}

	const useStyles = makeStyles((theme) => ({
	  root: {
	    flexGrow: 1,
	    backgroundColor: theme.palette.background.paper,
	  },
	}));


@inject('channelStore')
@inject('channelFileStore')
@observer
class ChannelList extends React.Component {
	
	constructor(props) {
        super(props);

        this.state = {
            uploadFile: '',
            isDownloading: false,
            isUploading: false,
        };
        this.tableRef = React.createRef();
    }
	
    componentDidMount() {
    	this.props.channelStore.clearMovieStore();
    	this.props.channelStore.loadModules();
    	//this.props.channelStore.getActorList(this.props.userId);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {  
    	if(this.props.channelStore.isLoadFailed) {
            this.props.enqueueSnackbar("서버 목록 조회에 실패하였습니다", {
                variant: 'error'
            });
            this.props.channelStore.clearListState();
        }
    }

    handleOpenDialog = () => {
        this.props.channelStore.openDialog();
    };
    
    handleClickModuleRow = (module_id) => {
    // alert('module_id -> '+module_id+' 상세정보 조회 api ');
    	this.props.channelStore.loadChannelList();
        // this.props.history.push(`/servers/${serverId}`);
    };
    
    handleClickChannelRow = (module_id) => {
    // alert('module_id -> '+module_id+' 상세정보 조회 api ');
    	this.props.channelStore.loadChannelDetail(module_id);
        // this.props.history.push(`/servers/${serverId}`);
    };

    handleChangePagingPage = (event, page) => {
        this.props.channelStore.changePagingPage(page);
    };

    handleChangePagingRowsPerPage = (event) => {
        const newValue = event.target.value;
        this.props.channelStore.changePagingRowsPerPage(newValue);
    };
    
    handleSearchMovie = (e) => { 
    	 const name = e.target.name;
         let value = e.target.value;
         this.props.channelStore.changeNewMovie(name, value);
      	 
    };
   
    handleChangeNewMovie = (e) => {       
        const name = e.target.name;
        let value = e.target.value;
        this.props.channelStore.changeNewMovie(name, value);
    };
    
    handleClickSeletFile = (event) => {
        const file = event.target.files[0];

        this.setState({uploadFile: file});
    };
    
    handleChangeUploadMovieFile = (event) => {
        const name = event.target.name;
        if( event.target.files[0] ){
            const file = event.target.files[0];
           	this.props.channelStore.changeNewMovie(name, file);
        }
    };
    
    handleChangeUploadCoverFile = (event, langCd) => {
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.channelStore.changeNewCover(name, file , langCd);        	
        }
    };
        
    handleChangeUploadCoverFile__ = (event) => {
        const file = event.target.files[0];
        this.props.channelFileStore.changeUploadFile(file);
    };
    
    handleChangeNewMovieActivated = (event) => {
    	const name = event.target.name;
    	const activated = event.target.checked;

        this.props.channelStore.changeNewChannelActivated(name, activated);
    }
   
    handleInsertMovie = () => {   	
    	this.props.channelStore.addNewMovie();
    }
         
    handleChangeSeletecdActor = (event) => {
    	this.props.channelStore.changeSelectedActor(event.target.value);
    };
      
    handleClickRemoveActor = (actor_idx) => {
    	this.props.channelStore.removeActor(actor_idx);
    }    
    
    handleKeyPressChn = (event) => {
    	if (event.key === "Enter") {
    		this.props.channelStore.changeTagChn(event.target.name,event.target.value);
    	}
    };
    
    handleClickRemoveTag = (langCd,tag) => {
    	this.props.channelStore.removeTag(langCd,tag);
    } 

    render() {
     
        const { classes } = this.props;
        const { chipData,isLoading, modules, channels, paging, newChannel, newCoverChn, newCoverEng, newCoverJpn ,movieResult } = this.props.channelStore;
        const {isOpenFileDialog, isUploadable, isUploading, channelUserId, fileList, upload, uploadFile} = this.props.channelFileStore;
        const filteredActorList = toJS(this.props.channelStore.filteredActorList);
        const {selectedActorIdx} = this.props.channelStore;

        
       //  const classes = useStyles();
        const {value, setValue} = this.props.channelStore;
       // const { theme }= this.props;
        
        const handleChangeCoverTab = (event, newValue) => {
            setValue(newValue);
        	
           };
        
           const handleChange = (event, newValue) => {
    	    setValue(newValue);
    	  };

    
        
        return (
        	
            <Container component="main" className={classes.mainContainer}>
          
                <div className={classes.appBarSpacer} />
              
                <div className={classes.mainContent}>
                    <Toolbar className={classes.toolbar}>
                        <Typography variant="h6" component="h2">
                            모듈 목록
                        </Typography>
                        <div className={classes.grow}></div>
                       
                    </Toolbar>


                    <Paper className={classes.bodyPaper}>
                        <div className={classes.loadingProgress}>
                            {isLoading ? <LinearProgress className={classes.loadingProgress} /> : ''}
                        </div>

                        <Table className={classes.bodyTable}>
                            <TableHead>
                                <TableRow key="_head_">
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">module_id</Typography></TableCell>
                                    <TableCell align="center" style={{width: '25%'}}><Typography variant="subtitle2">module_name</Typography></TableCell>
                                    <TableCell align="center" style={{width: '5%'}}><Typography variant="subtitle2">parent_module</Typography></TableCell>
                                    <TableCell align="center" style={{width: '20%'}}><Typography variant="subtitle2">channel_type</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">has_selection</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">show_type</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">show_order</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">user_yn</Typography></TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {modules.slice().map((module) => (
                                    <TableRow key={'node-' + module.module_id} hover onClick={() => this.handleClickModuleRow(module.module_id)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{module.module_id}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.module_name}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.parent_module}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.channel_type === 1 ? '최상모듈' : '하위채널'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.has_selection  === true ? '채널별분류' : '전체'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.show_type === 1 ? '두개씩' : '한개씩'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.show_order}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{module.user_yn === true ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                       
                                    </TableRow>
                                ))}
                            </TableBody>

                          
                        </Table>
                    </Paper>
                    
                    <ChannelAddDialog />
                    
                    
                    <Toolbar className={classes.toolbar}>
                    <Typography variant="h6" component="h2">
                        채널 목록
                    </Typography>
                    <div className={classes.grow}></div>
                    <Button variant="contained" color="primary" endIcon={<AddIcon />} onClick={this.handleOpenDialog}>추가</Button>
                </Toolbar>


                <Paper className={classes.bodyPaper}>
                    <div className={classes.loadingProgress}>
                        {isLoading ? <LinearProgress className={classes.loadingProgress} /> : ''}
                    </div>

                    <Table className={classes.bodyTable}>
                        <TableHead>
                            <TableRow key="_head_">
                                <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">module_id</Typography></TableCell>
                                <TableCell align="center" style={{width: '25%'}}><Typography variant="subtitle2">module_name</Typography></TableCell>
                                <TableCell align="center" style={{width: '5%'}}><Typography variant="subtitle2">parent_module</Typography></TableCell>
                                <TableCell align="center" style={{width: '20%'}}><Typography variant="subtitle2">channel_type</Typography></TableCell>
                                <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">has_selection</Typography></TableCell>
                                <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">show_type</Typography></TableCell>
                                <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">show_order</Typography></TableCell>
                                <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">user_yn</Typography></TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {channels.slice(paging.page * paging.rowsPerPage, paging.page * paging.rowsPerPage + paging.rowsPerPage).map((module) => (
                                <TableRow key={'node-' + module.module_id} hover onClick={() => this.handleClickChannelRow(module.module_id)}>
                                    <TableCell align="center" component="th" scope="row"><Typography variant="body2">{module.module_id}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.module_name}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.parent_module}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.channel_type === 1 ? '최상모듈' : '하위채널'}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.has_selection  === true ? '채널별분류' : '전체'}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.show_type === 1 ? '두개씩' : '한개씩'}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.show_order}</Typography></TableCell>
                                    <TableCell align="center"><Typography variant="body2">{module.user_yn === true ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                   
                                </TableRow>
                            ))}
                        </TableBody>

                        <TableFooter>
                            <TableRow>
                                <TablePagination rowsPerPageOptions={[5, 10, 15, 25, 50]}
                                                 count={channels.length} page={paging.page}
                                                 rowsPerPage={paging.rowsPerPage}
                                                 onChangePage={this.handleChangePagingPage}
                                                 onChangeRowsPerPage={this.handleChangePagingRowsPerPage}
                                />
                            </TableRow>
                        </TableFooter>
                    </Table>
                </Paper>
                
                
                    
                    
                    <Paper className={classes.bodyPaper}>
                    
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="module_name" id="module_name" onChange={this.handleChangeNewMovie} value={newChannel.module_name} label="채널제목(중)" />
	                    <TextField className={classes.movieDetail} name="module_name_eng" id="module_name_eng" onChange={this.handleChangeNewMovie} value={newChannel.module_name_eng} label="채널제목(영)" />                   
	                    <TextField className={classes.movieDetail} name="module_name_jpn" id="module_name_jpn" onChange={this.handleChangeNewMovie} value={newChannel.module_name_jpn} label="채널제목(일)" />
	                    
                    
	                    </form>
	                   	                   
	                    {<VideoLibrary  style={{ color: green[500],fontSize: 50 }} />}
	                    	
	                   
    	                
    	                
    	                <FormLabel className={classes.movieDetail} component="legend">모듈/채널 구분 channel_type</FormLabel>
	    	            <FormGroup row className={classes.movieDetail} >
		               
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
                       </FormGroup>
    	                    
    	                <FormLabel className={classes.movieDetail} component="legend">노출 분류 방식has_selection</FormLabel>
	    	            <FormGroup row className={classes.movieDetail} >
		               
	                	<FormControlLabel
                        value="true"
                        name="has_selection"
                        onChange={this.handleChangeNewMovieActivated}	
                        checked={newChannel.has_selection === true}
                        control={<Radio color="primary" />}
                        label="채널별"
                      />
                      <FormControlLabel
                        value="false"
                        name="has_selection"
    	                onChange={this.handleChangeNewMovieActivated}	
                        checked={newChannel.has_selection === false}
    	                control={<Radio color="primary" />}
                        label="전체"
                      />                 
                       </FormGroup>
				               
	    	            <FormLabel className={classes.movieDetail} component="legend">리스트 ROW당 노출 갯수 show_type</FormLabel>
	    	            <FormGroup row className={classes.movieDetail} >
		                	
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
                       </FormGroup>
			                
			                
			        
                <FormGroup  row className={classes.switchButton}>
             
                <Typography component="div">
                <FormLabel component="legend">노출 유무 </FormLabel>
                미노출 <FormControlLabel
                control={<Switch  checked={newChannel.user_yn} onChange={this.handleChangeNewMovieActivated} name="user_yn" />}
                label="노출"
              />
                	</Typography>
              
                </FormGroup>
                
            
                
                  
	                  
	                  
	                  
                  </Paper>
                  
                </div>
                
               
                <Toolbar className={classes.toolbar}>
	                <div className={classes.delGrow}></div>
	                <Button className={classes.delGrow} variant="contained" color="notice" endIcon={<ClearIcon />} onClick={this.handleOpenDialog}>삭제</Button>
	                <div className={classes.grow}></div>
	                <Button variant="contained" color="primary" endIcon={<DoneIcon />} onClick={this.handleOpenDialog}>수정</Button>
	                <div>{'\u00A0'}</div>
	                {movieResult === null ?   
	                <Button variant="contained" color="secondary" endIcon={<AddIcon />} onClick={this.handleInsertMovie}>추가</Button>
	                : <CircularProgress className={classes.CProgress} size={66}/>}
	            </Toolbar>
	            
                
            </Container>
        );
    }
};

export default withSnackbar(withRouter(withStyles(styles) (ChannelList)));