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
    Toolbar,
    Typography
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
//import ServerAddDialog from "./ServerAddDialog";

import {CircularProgress, Grid} from "@material-ui/core";
import MaterialTable, {MTableToolbar} from "material-table";
import axios from "axios";
import AddRoundedIcon from '@material-ui/icons/AddRounded';
import GetAppIcon from "@material-ui/icons/GetApp";
import fileDownload from "js-file-download";


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
	
	
});

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
        this.props.movieStore.loadMovies();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
    
    	if(this.props.movieStore.isLoadFailed) {
            this.props.enqueueSnackbar("서버 목록 조회에 실패하였습니다", {
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

    handleClickMovieRow = (mov_id) => {
    	alert('mov_id -> '+mov_id+' 상세정보 조회 api ');
    
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
           	this.props.movieStore.changeNewMovie(name, file);
        }
    };
    
    handleChangeUploadCoverFile = (event) => {
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.movieStore.changeNewCover(name, file);        	
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
    	this.props.movieStore.addNewMovie();
    }

    render() {
     
        const { classes } = this.props;
        const { isLoading, movies, paging, newMovie, newCover } = this.props.movieStore;
        const {isOpenFileDialog, isUploadable, isUploading, channelUserId, fileList, upload, uploadFile} = this.props.channelFileStore;
   
        
        return (
        	
            <Container component="main" className={classes.mainContainer}>
                <div className={classes.appBarSpacer} />
      
                <div>
		           
			    	<FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">모듈</InputLabel>
			        <Select
			          labelId="demo-simple-select-label"
			          id="module_id"
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
                                    <TableRow key={'node-' + movie.mov_id} hover onClick={() => this.handleClickMovieRow(movie.mov_id)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{movie.mov_id}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mov_name}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mins}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.mov_provider === 'External' ? '외부' : '내부'}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{movie.show_yn === 1 ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                       
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
                    
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="mov_name" id="mov_name" onChange={this.handleChangeNewMovie} value={newMovie.mov_name} label="영화제목(중)" />
	                    <TextField className={classes.movieDetail} id="standard-basic" label="영화제목(영)" />                   
	                    <TextField className={classes.movieDetail} id="standard-basic" label="영화제목(일)" />
	                    </form>
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="mins" id="mins" onChange={this.handleChangeNewMovie} value={newMovie.mins} label="재생시간" />
	                    <TextField className={classes.movieDetail} id="standard-basic" label="출연배우" />
	                    <TextField className={classes.movieDetail} id="standard-basic" label="관련태그" /> 
	                    </form>
	                   
	                    
	                    {<DoneIcon size="24" color="red"/>}
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} id="standard-basic" variant="outlined" size="small"  label="영상도메인주소"/> 
		                    <FormGroup  row className={classes.switchButton}>
	                    
	                    
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.has_down} onChange={this.handleChangeNewMovieActivated} name="has_down" />}
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
	                    
	                    {<DoneIcon size="24" color="red"/>} 
	                    <form className={classes.movieDetail} noValidate autoComplete="off">	             
	                    <TextField className={classes.movieDetail} id="standard-basic" variant="outlined" size="small" label="커버도메인" />
	                    
	                    <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newCover.horizontal_large.name}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="horizontal_large" style={{borderRadius:'25px'}}>가로대커버선택</Button>
                            <input id="horizontal_large" name="horizontal_large" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadCoverFile} />
                        </div>
                        </Grid>
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newCover.horizontal_small.name}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="horizontal_small" style={{borderRadius:'25px'}}>가로소커버선택</Button>
                            <input id="horizontal_small" name="horizontal_small" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadCoverFile} />
                        </div>
                        </Grid> 
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newCover.vertical_large.name}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="vertical_large" style={{borderRadius:'25px'}}>세로대커버선택</Button>
                            <input id="vertical_large" name="vertical_large" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadCoverFile} />
                        </div>
                        </Grid> 
                        
                        <Grid style={{display: 'flex'}}>
                        <TextField className={classes.movieDetail} value={newCover.vertical_small.name}  variant="outlined" size="small" label="" />
                        <div className={classes.filebox}>
                            <Button component="label" variant="contained" htmlFor="vertical_small" style={{borderRadius:'25px'}}>세로소커버선택</Button>
                            <input id="vertical_small" name="vertical_small" type="file" className={classes.fileSelection} onChange={this.handleChangeUploadCoverFile} />
                        </div>
                        </Grid> 
                       
	                    	
	                    </form>
	                  	                    
	                    <FormControl className={classes.formControl} component="fieldset">
	                    <FormLabel component="legend"></FormLabel>
	                    <FormGroup  row className={classes.switchButton}>
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.has_down} onChange={this.handleChangeNewMovieActivated} name="has_down" />}
	                        label="다운로드노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch  checked={newMovie.has_love} onChange={this.handleChangeNewMovieActivated} name="has_love" />}
	                        label="호감노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch   checked={newMovie.has_up} onChange={this.handleChangeNewMovieActivated} name="has_up" />}
	                        label="좋아요노출여부"
	                      />
	                      <FormControlLabel
	                        control={<Switch   checked={newMovie.has_diss} onChange={this.handleChangeNewMovieActivated} name="has_diss" />}
	                        label="싫어요노출여부"
	                      />
	                    </FormGroup>
	                    <FormHelperText>Be careful</FormHelperText>
	                  </FormControl>
	                  
	                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="영화소개"
		                  multiline
		                  rows={4}
		                  defaultValue="본 영화는...."
		                  variant="outlined"
		                />
		              </FormControl>
		                 

                  
                  </Paper>
                  
                </div>
                
                <Toolbar className={classes.toolbar}>
	                <div className={classes.delGrow}></div>
	                <Button className={classes.delGrow} variant="contained" color="notice" endIcon={<ClearIcon />} onClick={this.handleOpenDialog}>삭제</Button>
	                <div className={classes.grow}></div>
	                <Button variant="contained" color="primary" endIcon={<DoneIcon />} onClick={this.handleOpenDialog}>수정</Button>
	                <div>{'\u00A0'}</div><Button variant="contained" color="secondary" endIcon={<AddIcon />} onClick={this.handleInsertMovie}>추가</Button>
	            </Toolbar>
                
            </Container>
        );
    }
};

export default withSnackbar(withRouter(withStyles(styles) (MovieList)));