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
	
	actorDetail: {
	    marginLeft: theme.spacing(2),
	    padding: 2,
	    minWidth: '50%',
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
     
      profile: {
  	//    flexGrow: 1,
  	    backgroundColor: theme.palette.background.paper,
  	    margin: 15,
  	    borderRadius: 5,
  	    border: '1px solid #e0e0e0',
  	  },
	
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

    handleClickActorRow = (mov_id) => {
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
      	 this.props.userStore.loadMovies();
    };
   
    handleChangeNewActor = (e) => {       
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
        const { chipData,isLoading, actors, paging, newMovie, newCoverChn, newCoverEng, newCoverJpn ,movieResult } = this.props.userStore;
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
			        <Paper component="form" className={classes.formControl}>
				      <InputBase
				        className={classes.input}
				        placeholder="Search Actor Name"
				        inputProps={{ 'aria-label': 'search google maps' }}
				      />
				      <IconButton className={classes.iconButton} name="srch actor name"
						    value={newMovie.srch_mov_name} onClick={this.handleSearchMovie} aria-label="search">
				        <SearchIcon />
				      </IconButton>				        
				      </Paper>
				  </FormControl>			      
			    </div>
    
    
                <div className={classes.mainContent}>
                    <Toolbar className={classes.toolbar}>
                        <Typography variant="h6" component="h2">
                            배우 목록
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
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">actor_idx</Typography></TableCell>
                                    <TableCell align="center" style={{width: '30%'}}><Typography variant="subtitle2">actor_name</Typography></TableCell>
                                    <TableCell align="center" style={{width: '20%'}}><Typography variant="subtitle2">videos_count</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">star_level</Typography></TableCell>
                                    
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {actors.slice(paging.page * paging.rowsPerPage, paging.page * paging.rowsPerPage + paging.rowsPerPage).map((actor) => (
                                    <TableRow key={'node-' + actor.actor_idx} hover onClick={() => this.handleClickActorRow(actor.actor_idx)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{actor.actor_idx}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{actor.actor_name}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{actor.videos_count}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{actor.star_level}</Typography></TableCell>                                       
                                    </TableRow>
                                ))}
                            </TableBody>

                            <TableFooter>
                                <TableRow>
                                    <TablePagination rowsPerPageOptions={[5, 10, 15, 25, 50]}
                                                     count={actors.length} page={paging.page}
                                                     rowsPerPage={paging.rowsPerPage}
                                                     onChangePage={this.handleChangePagingPage}
                                                     onChangeRowsPerPage={this.handleChangePagingRowsPerPage}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </Paper>
                    
                    
                    <Paper className={classes.bodyPaper}>
                    
                    
                    <Grid container spacing={0}> 
	                    <Grid item xs={12} sm={3}>
	                    
	                    <Typography variant="h6" component="h2" className={classes.profile}>
			                  <img src={newMovie.photo_url ? newMovie.photo_url : '/images/vj0.png'} height="200" width="200" alt="actor"/>
		                	
			                  <div className={classes.filebox}>
			                  <Button component="label" variant="contained" htmlFor="f240p" style={{borderRadius:'25px'}}>프로파일선택</Button>
			                  <input id="f240p" name="f240p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
			                  </div>
			                </Typography>
	                    </Grid>
	                    
	                    <Grid item xs={12} sm={9}>
	
	                    <FormControl className={classes.movieDetail}>
				        <TextField className={classes.movieDetail} name="actor_name" id="actor_name" onChange={this.handleChangeNewActor} value={newMovie.mov_name} label="배우명(중)" />
				              <TextField className={classes.movieDetail} name="actor_name_eng" id="actor_name_eng" onChange={this.handleChangeNewActor} value={newMovie.mov_name_eng} label="배우명(영)" />                   
				              <TextField className={classes.movieDetail} name="actor_name_jpn" id="actor_name_jpn" onChange={this.handleChangeNewActor} value={newMovie.mov_name_jpn} label="배우명(일)" />
				    
				        </FormControl>
				       
				        
	                    </Grid>
                    </Grid>
                     
                    
                    <FormGroup  row className={classes.movieDetail}>
                    <TextField className={classes.movieDetailHalf} name="height" id="height" onChange={this.handleChangeNewActor} value={newMovie.mov_name} label="height" />
			              <TextField className={classes.movieDetailHalf} name="bust" id="bust" onChange={this.handleChangeNewActor} value={newMovie.mov_name_eng} label="bust" />
			            	  <TextField className={classes.movieDetailHalf} name="hips" id="hips" onChange={this.handleChangeNewActor} value={newMovie.mov_name} label="hips" />
				              <TextField className={classes.movieDetailHalf} name="waist" id="waist" onChange={this.handleChangeNewActor} value={newMovie.mov_name_eng} label="waist" /> 
				              <TextField className={classes.movieDetailHalf} name="cup" id="cup" onChange={this.handleChangeNewActor} value={newMovie.mov_name_eng} label="cup" /> 
                  </FormGroup>
                                      
	                    <FormControl className={classes.formControl} component="fieldset">
	                    <FormLabel component="legend"></FormLabel>
	                    <FormGroup  row className={classes.switchButton}>
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.has_down} onChange={this.handleChangeNewMovieActivated} name="has_down" />}
	                        label="노출유무"
	                      />
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.has_love} onChange={this.handleChangeNewMovieActivated} name="has_love" />}
	                        label="호감노출여부"
	                      />
	                     
	                    </FormGroup>
	                  
	                  </FormControl>
	                  
	                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="배우소개(중)"
		                  multiline
		                  rows={4}
		                  value={newMovie.mov_desc} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewActor} 
		                />
		              </FormControl>
		                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="배우소개(영)"
		                  multiline
		                  rows={4}
		                  value={newMovie.mov_desc_eng} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewActor} 
		                />
		              </FormControl>
		                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="배우소개(일)"
		                  multiline
		                  rows={4}
		                  value={newMovie.mov_desc_jpn} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewActor} 
		                />
		              </FormControl>
		                 

                  
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

export default withSnackbar(withRouter(withStyles(styles) (ActorList)));