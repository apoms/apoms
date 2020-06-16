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


@inject('movieStore')
@inject('channelFileStore')
@observer
class MovieList extends React.Component {
	
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
    	this.props.movieStore.clearMovieStore();
    	alert("componentDidMount");
    	this.props.movieStore.getDefaultChannelList("CHN");
    	this.props.movieStore.getActorList(this.props.userId);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {  
    	if(this.props.movieStore.isLoadFailed) {
            this.props.enqueueSnackbar("조회에 실패하였습니다", {
                variant: 'error'
            });
            this.props.movieStore.clearListState();
        }
    }

    handleOpenDialog = () => {
        this.props.movieStore.openDialog();
    };
    
    handleChangeModule = (e) => {
   	 	const name = e.target.name;
        let value = e.target.value;
        this.props.movieStore.changeNewMovie(name, value);
   };

    handleClickMovieRow = (movId) => {
    // alert('movId -> '+movId+' 상세정보 조회 api ');
    	this.props.movieStore.loadMovieDetail();
        // this.props.history.push(`/servers/${serverId}`);
    };

    handleChangePagingPage = (event, page) => {
        this.props.movieStore.changePagingPage(page);
    };

    handleChangePagingRowsPerPage = (event) => {
        const newValue = event.target.value;
        this.props.movieStore.changePagingRowsPerPage(newValue);
    };
    
    handleSearchMovie = (e) => { 
    	 const name = e.target.name;
         let value = e.target.value;
         this.props.movieStore.changeNewMovie(name, value);
      	 this.props.movieStore.loadMovies();
    };
   
    handleChangeNewMovie = (e) => {       
        const name = e.target.name;
        let value = e.target.value;
        this.props.movieStore.changeNewMovie(name, value);
    };
    
    handleClickSeletFile = (event) => {
        const file = event.target.files[0];

        this.setState({uploadFile: file});
    };
    
    handleChangeUploadMovieFile = (event) => {
        const name = event.target.name;
        if( event.target.files[0] ){
            const file = event.target.files[0];
           	this.props.movieStore.changeNewMovieFile(name, file);
        }
    };
    
    handleChangeUploadCoverFile = (event, langCd) => {
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.movieStore.changeNewCover(name, file , langCd);        	
        }
    };
        
    handleChangeUploadCoverFile__ = (event) => {
        const file = event.target.files[0];
        this.props.channelFileStore.changeUploadFile(file);
    };
    
    handleChangeNewMovieActivated = (event) => {
    	const name = event.target.name;
    	const activated = event.target.checked;

        this.props.movieStore.changeNewMovieActivated(name, activated);
    }
   
    handleInsertMovie = () => {   	
    	this.props.movieStore.uploadNewMovie();
    }
         
    handleChangeSeletecdActor = (event) => {
    	this.props.movieStore.changeSelectedActor(event.target.value);
    };
      
    handleClickRemoveActor = (actorIdx) => {
    	this.props.movieStore.removeActor(actorIdx);
    }    
    
    handleKeyPressChn = (event) => {
    	if (event.key === "Enter") {
    		this.props.movieStore.changeTagChn(event.target.name,event.target.value);
    	}
    };
    
    handleClickRemoveTag = (langCd,tag) => {
    	this.props.movieStore.removeTag(langCd,tag);
    } 
    
    handleChangeCoverDomain= (e) => {       
        const name = e.target.name;
        let value = e.target.value;
        this.props.movieStore.changeCoverDomain(name, value);
    };
    

    render() {
     
        const { classes } = this.props;
        const { chipData,isLoading, defaultchannels, movies, paging, newMovie, newCoverChn, newCoverEng, newCoverJpn ,movieResult, coverDomain } = this.props.movieStore;
        const {isOpenFileDialog, isUploadable, isUploading, channelUserId, fileList, upload, uploadFile} = this.props.channelFileStore;
        const filteredActorList = toJS(this.props.movieStore.filteredActorList);
        const {selectedActorIdx} = this.props.movieStore;

        
       //  const classes = useStyles();
        const {value, setValue} = this.props.movieStore;
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
			        <InputLabel id="demo-simple-select-label">모듈</InputLabel>
			        <Select
			          labelId="demo-simple-select-label"
			          id="moduleId"
			          name="module_Id"
			          value={newMovie.module_Id}
			          onChange={this.handleChangeModule}
			        >
			          <MenuItem value={10}>특집</MenuItem>
			          <MenuItem value={20}>국내</MenuItem>
			          <MenuItem value={30}>일본AV</MenuItem>
			        </Select>
			      </FormControl>
			      <FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">하위채널</InputLabel>
			        <Select
			        id="module_Id"
				    name="module_Id"
				    value={newMovie.module_Id}
				    onChange={this.handleChangeModule}
			        >
			          <MenuItem value={10}>여름특집</MenuItem>
			          <MenuItem value={20}>성탄특집</MenuItem>
			          <MenuItem value={30}>년말특집</MenuItem>
			        </Select>
			      </FormControl>
			      
			      
			      <FormControl className={classes.formControl}>				     
			        <Paper component="form" className={classes.formControl}>
				      <InputBase
				        className={classes.input}
				        placeholder="Search Movie Name"
				        inputProps={{ 'aria-label': 'search google maps' }}
				      />
				      <IconButton className={classes.iconButton} name="srchMovName"
						    value={newMovie.srchMovName} onClick={this.handleSearchMovie} aria-label="search">
				        <SearchIcon />
				      </IconButton>				        
				      </Paper>
				  </FormControl>			      
			    </div>
    
    
                <div className={classes.mainContent}>
                    <Toolbar className={classes.toolbar}>
                        <Typography variant="h6" component="h2">
                            동영상 목록
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
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">번호</Typography></TableCell>
                                    <TableCell align="center" style={{width: '30%'}}><Typography variant="subtitle2">영상제목</Typography></TableCell>
                                    <TableCell align="center" style={{width: '20%'}}><Typography variant="subtitle2">상영시간</Typography></TableCell>
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">출처</Typography></TableCell>
                                    <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">노출유무</Typography></TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {movies.slice(paging.page * paging.rowsPerPage, paging.page * paging.rowsPerPage + paging.rowsPerPage).map((movie) => (
                                    <TableRow key={'node-' + movie.movId} hover onClick={() => this.handleClickMovieRow(movie.movId)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{movie.movId}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.movName}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mins}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.movProvider === true ? '외부' : '내부'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.showYn === 1 ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                       
                                    </TableRow>
                                ))}
                            </TableBody>

                            <TableFooter>
                                <TableRow>
                                    <TablePagination rowsPerPageOptions={[5, 10, 15, 25, 50]}
                                                     count={movies.length} page={paging.page}
                                                     rowsPerPage={paging.rowsPerPage}
                                                     onChangePage={this.handleChangePagingPage}
                                                     onChangeRowsPerPage={this.handleChangePagingRowsPerPage}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </Paper>
                    
                    
                    <Paper className={classes.bodyPaper}>
                    
                    <FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">모듈</InputLabel>
			        <Select
			          labelId="demo-simple-select-label"
			          id="moduleId"
			          name="module_Id"
			          value={newMovie.module_Id}
			          onChange={this.handleChangeModule}
			        >
			          
			          
			          <option key={''} value={''}></option>
                      {defaultchannels.map(channels =>
                          <option key={`${channels.id}`} value={channels.id}>{channels.name}</option>
                      )}   
                      
                      
			        </Select>
			      </FormControl>
			      <FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">하위채널</InputLabel>
			        <Select
			        id="module_Id"
				    name="module_Id"
				    value={newMovie.module_Id}
				    onChange={this.handleChangeModule}
			        >
			          <MenuItem value={10}>여름특집</MenuItem>
			          <MenuItem value={20}>성탄특집</MenuItem>
			          <MenuItem value={30}>년말특집</MenuItem>
			        </Select>
			      </FormControl>
			      
                    
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="movName" id="movName" onChange={this.handleChangeNewMovie} value={newMovie.movName} label="영화제목(중)" />
	                    <TextField className={classes.movieDetail} name="movNameEng" id="movNameEng" onChange={this.handleChangeNewMovie} value={newMovie.movNameEng} label="영화제목(영)" />                   
	                    <TextField className={classes.movieDetail} name="movNameJpn" id="movNameJpn" onChange={this.handleChangeNewMovie} value={newMovie.movNameJpn} label="영화제목(일)" />
	                    </form>
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="mins" id="mins" onChange={this.handleChangeNewMovie} value={newMovie.mins} label="재생시간" />
	                   
	                    <FormControl className={classes.movieDetailHalf}>
	                 
	                    <InputLabel id="demo-simple-select-label">출연배우</InputLabel>
				        <Select labelId="channel-member-group-label"
                            id="channel-member-group-select"
                            className={classes.itemBody2}
                            value={selectedActorIdx}
                            onChange={this.handleChangeSeletecdActor}
                            native
                    >
                        <option key={''} value={''}></option>
                        {filteredActorList.map(actor =>
                            <option key={`${actor.actorIdx}`} value={actor.actorIdx}>{actor.actorName}</option>
                        )}                        
                    </Select>
                    </FormControl>
                    <FormControl className={classes.movieDetailHalf}>
	                    <div style={{marginLeft: 6,marginTop: 6}}>
	                     
	                        {newMovie.actorList.map((actor, index) =>
	                            <Chip key={`${actor.actorIdx}`} className={classes.chip} label={actor.actorName} onDelete={() => this.handleClickRemoveActor(actor.actorIdx)} />
	                        )}
	                    </div>
	                </FormControl>
				       
				        <FormControl className={classes.movieDetailHalf}>
	                    <TextField name="CHN" label="태그(중)" onKeyPress={this.handleKeyPressChn} /> 
	                    </FormControl>
	                    <FormControl className={classes.movieDetailHalf}>
	                    
		                <div style={{marginLeft: 6, marginTop: 6}}>
	                     
	                        {newMovie.tagListChn.map((tag, index) =>
	                            <Chip key={`${tag.name}`} className={classes.chip} label={tag.name} onDelete={() => this.handleClickRemoveTag('CHN',tag.name)} />
	                        )}
	                    </div>       
				        </FormControl>
				        
				        <FormControl className={classes.movieDetailHalf}>
	                    <TextField name="ENG" label="태그(영)" onKeyPress={this.handleKeyPressChn} /> 
	                    </FormControl>
	                    <FormControl className={classes.movieDetailHalf}>
	                    
		                <div style={{marginLeft: 6, marginTop: 6}}>
	                     
	                        {newMovie.tagListEng.map((tag, index) =>
	                            <Chip key={`${tag.name}`} className={classes.chip} label={tag.name} onDelete={() => this.handleClickRemoveTag('ENG',tag.name)} />
	                        )}
	                    </div>       
				        </FormControl>
				        
				        <FormControl className={classes.movieDetailHalf}>
	                    <TextField name="JPN" label="태그(일)" onKeyPress={this.handleKeyPressChn} /> 
	                    </FormControl>
	                    <FormControl className={classes.movieDetailHalf}>
	                    
		                <div style={{marginLeft: 6, marginTop: 6}}>
	                     
	                        {newMovie.tagListJpn.map((tag, index) =>
	                            <Chip key={`${tag.name}`} className={classes.chip} label={tag.name} onDelete={() => this.handleClickRemoveTag('JPN',tag.name)} />
	                        )}
	                    </div>       
	                    </FormControl>
	                    <FormHelperText>태그입력후 엔터키를 치세요.</FormHelperText>
	                    
	                    </form>
	                   
	                    {<VideoLibrary  style={{ color: green[500],fontSize: 50 }} />}
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="domain" variant="outlined" onChange={this.handleChangeNewMovie} value={newMovie.domain}  size="small"  label="영상도메인주소"/> 
		                    <FormGroup  row className={classes.switchButton}>
	                    
	                    
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.movProvider} onChange={this.handleChangeNewMovieActivated} name="movProvider" />}
	                        label="외부도메인여부"
	                      />
	                    </FormGroup>
	                    <FormHelperText>영상위치가 외부도메인일 경우 체크하세요.</FormHelperText>
	                    		
	                    
                        <Grid style={{display: 'flex'}}>
	                        <TextField className={classes.movieDetail} value={newMovie.f240p.name ? newMovie.f240p.name : newMovie.f240p}  variant="outlined" size="small" label="" />
	                        <div className={classes.filebox}>
	                            <Button component="label" variant="contained" htmlFor="f240p" style={{borderRadius:'25px'}}>240p영상선택</Button>
	                            <input id="f240p" name="f240p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
	                        </div>
	                    </Grid> 
	                    
	                    <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newMovie.f360p.name ? newMovie.f360p.name : newMovie.f360p} variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="f360p" style={{borderRadius:'25px'}}>360p영상선택</Button>
                            <input id="f360p" name="f360p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
                        </div>
                        </Grid>
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newMovie.f480p.name ? newMovie.f480p.name : newMovie.f480p}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="f480p" style={{borderRadius:'25px'}}>480p영상선택</Button>
                            <input id="f480p" name="f480p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
                        </div>
                        </Grid> 
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newMovie.f720p.name ? newMovie.f720p.name : newMovie.f720p}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="f720p" style={{borderRadius:'25px'}}>720p영상선택</Button>
                            <input id="f720p" name="f720p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
                        </div>
                        </Grid> 
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newMovie.f1080p.name ? newMovie.f1080p.name : newMovie.f1080p}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="f1080p" style={{borderRadius:'25px'}}>1080p영상선택</Button>
                            <input id="f1080p" name="f1080p" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadMovieFile} />
                        </div>
                        </Grid> 
                       
	                    </form>
	                    
	                    {<ImageIcon  style={{ color: green[500],fontSize: 50 }} />}
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    
	                    
	                    <TextField className={classes.movieDetail} name="coverDomain" id="standard-basic" value={coverDomain} 
	                    onChange={this.handleChangeCoverDomain} variant="outlined" size="small" label="커버도메인" />
	                    	
	                    
	                    <FormControl className={classes.movieDetail}>
		                    <Paper square>
		                    
		                    <Tabs
		                      value={value}
		                      indicatorColor="primary"
		                      textColor="primary"
		                      onChange={handleChange}
		                      aria-label="disabled tabs example"
		                    >
		                      <Tab label="커버 이미지 (중)" />
		                      <Tab label="커버 이미지 (영)"/>
		                      <Tab label="커버 이미지 (일)" />
		                    </Tabs>
		                  </Paper>
		                  </FormControl>
		                  
		                    <SwipeableViews                      
		                      index={value}
		                      onChangeIndex={handleChangeCoverTab}
		                    >
		                      <TabPanel value={value} index={0} >
		                      
		                      
		                      <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverChn.horizontalLarge.name ? newCoverChn.horizontalLarge.name : newCoverChn.horizontalLarge}  variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_large_chn" style={{borderRadius:'25px'}}>가로대선택(중)</Button>
		                            <input id="horizontal_large_chn" name="horizontalLarge" type="file" className={classes.fileSelection} onChange=
		                            {(e) => this.handleChangeUploadCoverFile(e,'CHN')}
		                             />
		                        </div>
		                        </Grid>
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverChn.horizontalSmall.name ? newCoverChn.horizontalSmall.name : newCoverChn.horizontalSmall}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_small_chn" style={{borderRadius:'25px'}}>가로소커버선택</Button>
		                            <input id="horizontal_small_chn" name="horizontalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'CHN')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverChn.verticalLarge.name ? newCoverChn.verticalLarge.name : newCoverChn.verticalLarge}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_large_chn" style={{borderRadius:'25px'}}>세로대커버선택</Button>
		                            <input id="vertical_large_chn" name="verticalLarge" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'CHN')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverChn.verticalSmall.name ? newCoverChn.verticalSmall.name : newCoverChn.verticalSmall}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_small_chn" style={{borderRadius:'25px'}}>세로소커버선택</Button>
		                            <input id="vertical_small_chn" name="verticalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'CHN')} />
		                        </div>
		                        </Grid> 
		                       
		                      
		                      
		                      
		                      
		                      </TabPanel>
		                      <TabPanel value={value} index={1} >
		                      <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverEng.horizontalLarge.name ? newCoverEng.horizontalLarge.name : newCoverEng.horizontalLarge}  variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_large_eng" style={{borderRadius:'25px'}}>가로대선택(영)</Button>
		                            <input id="horizontal_large_eng" name="horizontalLarge" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'ENG')} />
		                        </div>
		                        </Grid>
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverEng.horizontalSmall.name ? newCoverEng.horizontalSmall.name : newCoverEng.horizontalSmall}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_small_eng" style={{borderRadius:'25px'}}>가로소커버선택</Button>
		                            <input id="horizontal_small_eng" name="horizontalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'ENG')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverEng.verticalLarge.name ? newCoverEng.verticalLarge.name : newCoverEng.verticalLarge}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_large_eng" style={{borderRadius:'25px'}}>세로대커버선택</Button>
		                            <input id="vertical_large_eng" name="verticalLarge" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'ENG')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverEng.verticalSmall.name ? newCoverEng.verticalSmall.name : newCoverEng.verticalSmall}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_small_eng" style={{borderRadius:'25px'}}>세로소커버선택</Button>
		                            <input id="vertical_small_eng" name="verticalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'ENG')} />
		                        </div>
		                        </Grid> 

		                      
		                      
		                      
		                      </TabPanel>
		                      <TabPanel value={value} index={2} >

		                      <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverJpn.horizontalLarge.name ? newCoverJpn.horizontalLarge.name : newCoverJpn.horizontalLarge}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_large_jpn" style={{borderRadius:'25px'}}>가로대선택(일)</Button>
		                            <input id="horizontal_large_jpn" name="horizontalLarge" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'JPN')} />
		                        </div>
		                        </Grid>
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverJpn.horizontalSmall.name ? newCoverJpn.horizontalSmall.name : newCoverJpn.horizontalSmall}  variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="horizontal_small_jpn" style={{borderRadius:'25px'}}>가로소커버선택</Button>
		                            <input id="horizontal_small_jpn" name="horizontalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'JPN')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverJpn.verticalLarge.name ? newCoverJpn.verticalLarge.name : newCoverJpn.verticalLarge}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_large_jpn" style={{borderRadius:'25px'}}>세로대커버선택</Button>
		                            <input id="vertical_large_jpn" name="verticalLarge" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'JPN')} />
		                        </div>
		                        </Grid> 
		                        
		                        <Grid style={{display: 'flex'}}>
		                        <TextField className={classes.movieDetailCover} value={newCoverJpn.verticalSmall.name ? newCoverJpn.verticalSmall.name : newCoverJpn.verticalSmall}   variant="outlined" size="small" label="" />
		                        <div className={classes.fileboxCover}>
		                            <Button component="label" variant="contained" htmlFor="vertical_small_jpn" style={{borderRadius:'25px'}}>세로소커버선택</Button>
		                            <input id="vertical_small_jpn" name="verticalSmall" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadCoverFile(e,'JPN')} />
		                        </div>
		                        </Grid> 
		                      </TabPanel>
		                    </SwipeableViews>
	                  
	                    
	                    
	                    	
	                    </form>
	                  	                    
	                    <FormControl className={classes.formControl} component="fieldset">
	                    <FormLabel component="legend"></FormLabel>
	                    <FormGroup  row className={classes.switchButton}>
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.hasDown} onChange={this.handleChangeNewMovieActivated} name="hasDown" />}
	                        label="다운로드노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.hasLove} onChange={this.handleChangeNewMovieActivated} name="hasLove" />}
	                        label="호감노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch   checked={newMovie.hasUp} onChange={this.handleChangeNewMovieActivated} name="hasUp" />}
	                        label="좋아요노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch   checked={newMovie.hasDiss} onChange={this.handleChangeNewMovieActivated} name="hasDiss" />}
	                        label="싫어요노출여부"
	                      />
	                    </FormGroup>
	                    <FormHelperText>Be careful</FormHelperText>
	                  </FormControl>
	                  
	                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="영화소개(중)"
		                  multiline
		                  rows={4}
		                  value={newMovie.movDesc} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewMovie} 
		                />
		              </FormControl>
		                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="영화소개(영)"
		                  multiline
		                  rows={4}
		                  value={newMovie.movDescEng} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewMovie} 
		                />
		              </FormControl>
		                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="영화소개(일)"
		                  multiline
		                  rows={4}
		                  value={newMovie.movDescJpn} 
		                  variant="outlined"
		                  onChange={this.handleChangeNewMovie} 
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

export default withSnackbar(withRouter(withStyles(styles) (MovieList)));