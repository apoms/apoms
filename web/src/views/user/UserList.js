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
// import ServerAddDialog from "./ServerAddDialog";
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


@inject('userStore')
@inject('channelFileStore')
@observer
class ActorList extends React.Component {
	
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
    	this.props.userStore.clearMovieStore();
    	this.props.userStore.getActorList(this.props.userId);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {  
    	if(this.props.userStore.isLoadFailed) {
            this.props.enqueueSnackbar("서버 목록 조회에 실패하였습니다", {
                variant: 'error'
            });
            this.props.userStore.clearListState();
        }
    }

    handleOpenDialog = () => {
        this.props.userStore.openDialog();
    };
    
    handleChangeModule = (e) => {
   	 	const name = e.target.name;
        let value = e.target.value;
        this.props.userStore.changeNewMovie(name, value);
   };

    handleClickMovieRow = (mov_id) => {
    // alert('mov_id -> '+mov_id+' 상세정보 조회 api ');
    	this.props.userStore.loadMovieDetail();
        // this.props.history.push(`/servers/${serverId}`);
    };

    handleChangePagingPage = (event, page) => {
        this.props.userStore.changePagingPage(page);
    };

    handleChangePagingRowsPerPage = (event) => {
        const newValue = event.target.value;
        this.props.userStore.changePagingRowsPerPage(newValue);
    };
    
    handleSearchMovie = (e) => { 
    	 const name = e.target.name;
         let value = e.target.value;
         this.props.userStore.changeNewMovie(name, value);
      	 this.props.userStore.loadUsers();
    };
   
    handleChangeNewMovie = (e) => {       
        const name = e.target.name;
        let value = e.target.value;
        this.props.userStore.changeNewMovie(name, value);
    };
    
    handleClickSeletFile = (event) => {
        const file = event.target.files[0];

        this.setState({uploadFile: file});
    };
    
    handleChangeUploadMovieFile = (event) => {
        const name = event.target.name;
        if( event.target.files[0] ){
            const file = event.target.files[0];
           	this.props.userStore.changeNewMovie(name, file);
        }
    };
    
    handleChangeUploadCoverFile = (event, langCd) => {
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.userStore.changeNewCover(name, file , langCd);        	
        }
    };
        
    handleChangeUploadCoverFile__ = (event) => {
        const file = event.target.files[0];
        this.props.channelFileStore.changeUploadFile(file);
    };
    
    handleChangeNewMovieActivated = (event) => {
    	const name = event.target.name;
    	const activated = event.target.checked;

        this.props.userStore.changeNewMovieActivated(name, activated);
    }
   
    handleInsertMovie = () => {   	
    	this.props.userStore.addNewMovie();
    }
         
    handleChangeSeletecdActor = (event) => {
    	this.props.userStore.changeSelectedActor(event.target.value);
    };
      
    handleClickRemoveActor = (actor_idx) => {
    	this.props.userStore.removeActor(actor_idx);
    }    
    
    handleKeyPressChn = (event) => {
    	if (event.key === "Enter") {
    		this.props.userStore.changeTagChn(event.target.name,event.target.value);
    	}
    };
    
    handleClickRemoveTag = (langCd,tag) => {
    	this.props.userStore.removeTag(langCd,tag);
    } 

    render() {
     
        const { classes } = this.props;
        const { chipData,isLoading, users, paging, newMovie, newCoverChn, newCoverEng, newCoverJpn ,movieResult } = this.props.userStore;
        const {isOpenFileDialog, isUploadable, isUploading, channelUserId, fileList, upload, uploadFile} = this.props.channelFileStore;
        const filteredActorList = toJS(this.props.userStore.filteredActorList);
        const {selectedActorIdx} = this.props.userStore;

        
       //  const classes = useStyles();
        const {value, setValue} = this.props.userStore;
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
      
                
              
              
             
            
              
                <div>
		           
			    	<FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">이용자구분</InputLabel>
			        <Select
			          labelId="demo-simple-select-label"
			          id="module_id"
			          name="module_Id"
			          value={newMovie.module_Id}
			          onChange={this.handleChangeModule}
			        >
			          <MenuItem value={10}>전체</MenuItem>
			          <MenuItem value={20}>앵커(VJ)</MenuItem>
			          <MenuItem value={30}>일반</MenuItem>
			        </Select>
			      </FormControl>
			      
			      
			      
			      <FormControl className={classes.formControl}>				     
			        <Paper component="form" className={classes.formControl}>
				      <InputBase
				        className={classes.input}
				        placeholder="Search User Name"
				        inputProps={{ 'aria-label': 'search google maps' }}
				      />
				      <IconButton className={classes.iconButton} name="srch_mov_name"
						    value={newMovie.srch_mov_name} onClick={this.handleSearchMovie} aria-label="search">
				        <SearchIcon />
				      </IconButton>				        
				      </Paper>
				  </FormControl>			      
			    </div>
    
    
                <div className={classes.mainContent}>
                    <Toolbar className={classes.toolbar}>
                        <Typography variant="h6" component="h2">
                            이용자 목록
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
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">번호</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">nick_name</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">방송시간</Typography></TableCell>
                                    <TableCell align="center" style={{width: '20%'}}><Typography variant="subtitle2">방제목</Typography></TableCell>
                                    <TableCell align="center" style={{width: '25%'}}><Typography variant="subtitle2">요청일시</Typography></TableCell>
                                    <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">승인유무</Typography></TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {users.slice(paging.page * paging.rowsPerPage, paging.page * paging.rowsPerPage + paging.rowsPerPage).map((movie) => (
                                    <TableRow key={'node-' + movie.mov_id} hover onClick={() => this.handleClickMovieRow(movie.mov_id)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{movie.mov_id}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mov_name}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mins}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mov_provider === true ? '외부' : '내부'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">2020-06-12 13:00:22</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.show_yn === 1 ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                       
                                    </TableRow>
                                ))}
                            </TableBody>

                            <TableFooter>
                                <TableRow>
                                    <TablePagination rowsPerPageOptions={[5, 10, 15, 25, 50]}
                                                     count={users.length} page={paging.page}
                                                     rowsPerPage={paging.rowsPerPage}
                                                     onChangePage={this.handleChangePagingPage}
                                                     onChangeRowsPerPage={this.handleChangePagingRowsPerPage}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </Paper>
                    
                    
                   
                  
                </div>
                
               
                <Toolbar className={classes.toolbar}>
	                <div className={classes.delGrow}></div>
	                <Button className={classes.delGrow} variant="contained" color="notice" endIcon={<ClearIcon />} onClick={this.handleOpenDialog}>삭제</Button>
	                <div className={classes.grow}></div>
	                <Button variant="contained" color="primary" endIcon={<DoneIcon />} onClick={this.handleOpenDialog}>인증완료</Button>
	                <div>{'\u00A0'}</div>
	                {movieResult === null ?   
	                <Button variant="contained" color="secondary" endIcon={<AddIcon />} onClick={this.handleInsertMovie}>보류</Button>
	                : <CircularProgress className={classes.CProgress} size={66}/>}
	            </Toolbar>
	            
                
            </Container>
        );
    }
};

export default withSnackbar(withRouter(withStyles(styles) (ActorList)));