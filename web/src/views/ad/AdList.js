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

import {CircularProgress, Grid} from "@material-ui/core";
import MaterialTable, {MTableToolbar} from "material-table";
import axios from "axios";
import AddRoundedIcon from '@material-ui/icons/AddRounded';
import GetAppIcon from "@material-ui/icons/GetApp";
import fileDownload from "js-file-download";
import Chip from '@material-ui/core/Chip';
import TagFacesIcon from '@material-ui/icons/TagFaces';
import AttachmentSharpIcon from '@material-ui/icons/AttachmentSharp';
import {Link} from "react-router-dom";
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import SwipeableViews from 'react-swipeable-views';
import DeleteIcon from '@material-ui/icons/Delete';

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
    
    bodyPaper2: {
        width: '80%',
        marginTop: theme.spacing(1),
        marginLeft: theme.spacing(2),
        overflowX: 'auto',
    },
    
    bodyTable: {
        minWidth: 500,
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
	    minWidth: '20%',
	    //display: 'flex',
	},
	
	movieDetailLink: {
	    marginRight: theme.spacing(2),
	    padding: 2,
	    minWidth: '50%',
	    //display: 'flex',
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
    
    
    CProgress: {
    	left: '50%',
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

	

@inject('adStore')
@inject('channelFileStore')
@observer
class AdList extends React.Component {
	
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

    	this.props.adStore.clearAdStore();
       
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
  
    	if(this.props.adStore.isLoadFailed) {
            this.props.enqueueSnackbar("서버 목록 조회에 실패하였습니다", {
                variant: 'error'
            });

            this.props.adStore.clearListState();
        }
    	
    	
    }

    handleOpenDialog = () => {
        this.props.adStore.openDialog();
    };
    
    handleChangeLocation = (e) => {
   	 	const name = e.target.name;
        let value = e.target.value;
        this.props.adStore.changeNewShowCtg(name, value);
        
        this.props.adStore.loadAds();
        
   };

    handleClickAdRow = (ad_id) => {
    //	alert('mov_id -> '+mov_id+' 상세정보 조회 api ');
    
    	this.props.adStore.loadAdDetail();
        // this.props.history.push(`/servers/${serverId}`);
    };
    
    
    handleDelImageChnList  = (ad_img_idx) => {
   
    	this.props.adStore.delImageChnList(ad_img_idx);
    }
    
    handleChangePagingPage = (event, page) => {
        this.props.adStore.changePagingPage(page);
    };

    handleChangePagingRowsPerPage = (event) => {
        const newValue = event.target.value;

        this.props.adStore.changePagingRowsPerPage(newValue);
    };
    
    
    handleChangeActor = (e) => {       
        const name = e.target.name;
        let value = e.target.value;
      //  alert(name+":"+value);
       // this.props.adStore.changeNewMovie(name, value);
    };
    
    handleChangeNewAd = (e) => {       
        const name = e.target.name;
        let value = e.target.value;
        this.props.adStore.changeNewAd(name, value);
    };
    
    handleClickSeletFile = (event) => {
        const file = event.target.files[0];

        this.setState({uploadFile: file});
    };
    
    handleChangeUploadMovieFile = (event) => {
        const name = event.target.name;
        if( event.target.files[0] ){
            const file = event.target.files[0];
           	this.props.adStore.changeNewMovie(name, file);
        }
    };
    
    handleChangeUploadImageFile = (event, langCd) => {
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.adStore.changeNewCover(name, file, langCd);        	
        }
    };
    
    handleChangeLinkAddr = (event, langCd) => {
        const name = event.target.name;
        let value = event.target.value;
        this.props.adStore.changeLinkAddr(name, value, langCd);
    };
    
    handleAddImageChnList = (event) => {
    	
    	this.props.adStore.addImageChnList();
    /*	
        const name = event.target.name;
        if( event.target.files[0] ) { 
        	const file = event.target.files[0];
        	this.props.adStore.addImageChnList(name, file, langCd);        	
        }
        */
    };
    
    
    handleChangeUploadCoverFile__ = (event) => {
        const file = event.target.files[0];
        this.props.channelFileStore.changeUploadFile(file);
    };
    
    handleChangeNewAdActivated = (event) => {
    	const name = event.target.name;
    	const activated = event.target.checked;

        this.props.adStore.changeNewAdActivated(name, activated);
    }
   
    handleInsertMovie = () => {
    	this.props.adStore.addNewMovie();
    }
    
    handleDelete = (chipToDelete) => () => {
     //   setChipData((chips) => chips.filter((chip) => chip.key !== chipToDelete.key));
      };

    render() {
     
        const { classes } = this.props;
        const { chipData,isLoading, advertisements, paging, imageChnList, newShowCtg, newAd, newImageChn ,newImageEng ,newImageJpn ,addAdResult } = this.props.adStore;
        const {isOpenFileDialog, isUploadable, isUploading, channelUserId, fileList, upload, uploadFile} = this.props.channelFileStore;

        const {value, setValue} = this.props.adStore;
        const handleChangeCoverTab = (event, newValue) => {
            setValue(newValue);
        	
           };
        
           const handleChange = (event, newValue) => {
    	    setValue(newValue);
    	  };

    	  
        return (
                		
            <Container component="main" className={classes.mainContainer}>
                <div className={classes.appBarSpacer} />
      
         
		           
			    	<FormControl className={classes.formControl}>
			        <InputLabel id="demo-simple-select-label">광고 위치</InputLabel>
			        <Select
			          labelId="demo-simple-select-label"
			          id="show_location"
			          name="show_location"
			          value={newShowCtg.show_location}
			          onChange={this.handleChangeLocation}
			        >
			          <MenuItem value={0}>入境时 (진입)</MenuItem>
			          <MenuItem value={1}>首页频道  (HOME-메인)</MenuItem>
			          <MenuItem value={2}>专栏 (COLUMN-칼럼)</MenuItem>
			          <MenuItem value={3}>发现 (DISCOVER-발견)</MenuItem>
			          <MenuItem value={4}>我的  ( MINE 마이페이지)</MenuItem>
			          <MenuItem value={5}>视频播放前 (동영상 재생전)</MenuItem>
			          <MenuItem value={6}>影片详情  (Video details 동영상 상세페이지)</MenuItem>
			          <MenuItem value={7}>精选模块 (MODULES 짧은 비디오)</MenuItem>
			          <MenuItem value={8}>视频播放中 (Video playing 재생중 작은 광고란) </MenuItem>
			          <MenuItem value={9}>banner下方广告位 (Advertising space below banne) </MenuItem>
			        </Select>
			      </FormControl>
			      			      
			   
    
                <div className={classes.mainContent}>
                    <Toolbar className={classes.toolbar}>
           
                    <Typography variant="h6" component="h2">
                    <a href='http://118.67.170.98:8000/'  target="_blank" className={classes.link} >
                    	<img src={'/images/ad0'+newShowCtg.show_location+".png"} height="500" width="250" alt="show_location"/>
                    </a>
                   	</Typography>
                    
	              <Paper className={classes.bodyPaper2}>
	            
	              <Toolbar className={classes.toolbar}>
                  <Typography variant="h6" component="h2">
                      광고 목록
                  </Typography>
                  <div className={classes.grow}></div>
                 
                  </Toolbar>
                  
                        <Table className={classes.bodyTable}>
                            <TableHead>
                                <TableRow key="_head_">
                                    <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">번호</Typography></TableCell>
                                    <TableCell align="center" style={{width: '30%'}}><Typography variant="subtitle2">광고제목</Typography></TableCell>
                                    <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">노출유무</Typography></TableCell>
                                    <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">광고주</Typography></TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {advertisements.slice(paging.page * paging.rowsPerPage, paging.page * paging.rowsPerPage + paging.rowsPerPage).map((ad) => (
                                    <TableRow key={'node-' + ad.ad_id} hover onClick={() => this.handleClickAdRow(ad.ad_id)}>
                                        <TableCell align="center" component="th" scope="row"><Typography variant="body2">{ad.ad_id}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{ad.ad_name}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{ad.status === 1 ? <DoneIcon className={classes.greenIcon} /> : <ClearIcon className={classes.redIcon} />}</Typography></TableCell>
                                        <TableCell align="center"><Typography variant="body2">{ad.advertiser}</Typography></TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>

                            <TableFooter>
                                <TableRow>
                                    <TablePagination rowsPerPageOptions={[5, 10, 15, 25, 50]}
                                                     count={advertisements.length} page={paging.page}
                                                     rowsPerPage={paging.rowsPerPage}
                                                     onChangePage={this.handleChangePagingPage}
                                                     onChangeRowsPerPage={this.handleChangePagingRowsPerPage}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                            
                       
                        </Paper>
                        
                    </Toolbar>
                    
                    
                    <Paper className={classes.bodyPaper}>
                    
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} name="ad_name" id="ad_name" onChange={this.handleChangeNewAd} value={newAd.ad_name} label="광고제목" />
	                    </form>
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                   
	                    <FormControl className={classes.movieDetailHalf}>
				        <InputLabel id="demo-simple-select-label">노출시간</InputLabel>
				        <Select
				          labelId="demo-simple-select-label"
				          id="ad_show_time"
				          name="ad_show_time"
				          value={newAd.ad_show_time}
				          onChange={this.handleChangeNewAd}
				        >
				          <MenuItem value={10}>10sec</MenuItem>
				          <MenuItem value={20}>20sec</MenuItem>
				          <MenuItem value={30}>30sec</MenuItem>
				          
				        </Select>
				        </FormControl>
				        <FormControl className={classes.movieDetailHalf}>
				        <InputLabel id="demo-simple-select-label">건너뛰기시간</InputLabel>
				        <Select
				          labelId="demo-simple-select-label"
				          id="ad_skip_time"
				          name="ad_skip_time"
				          value={newAd.ad_skip_time}
				          onChange={this.handleChangeNewAd}
				        >
				          <MenuItem value={5}>5sec</MenuItem>
				          <MenuItem value={10}>10sec</MenuItem>
				          <MenuItem value={30}>30sec</MenuItem>
				          
				        </Select>
				        
				       
				        </FormControl>
				        <FormGroup  row className={classes.switchButton}>
	                      <FormControlLabel
	                        control={<Switch  checked={newAd.status} onChange={this.handleChangeNewAdActivated} name="status" />}
	                        label="광고노출유무"
	                      />
	                    </FormGroup>
	                    <FormHelperText>광고 확인 후 노출유무를 선택하세요.</FormHelperText>
	                    </form>
	                  
	                    {<AttachmentSharpIcon  style={{ color: green[500],fontSize: 50 }} />} 
		                
	                    <form className={classes.movieDetail} noValidate autoComplete="off">
	                    <TextField className={classes.movieDetail} id="standard-basic" variant="outlined" value={newAd.thumbnail_domain} name="thumbnail_domain" onChange={this.handleChangeNewAd} size="small"  label="광고이미지 도메인주소"/> 
                       
	                    	<FormControl className={classes.movieDetail}>
	                    <Paper square>
	                    
	                    <Tabs
	                      value={value}
	                      indicatorColor="primary"
	                      textColor="primary"
	                      onChange={handleChange}
	                      aria-label="disabled tabs example"
	                    >
	                      <Tab label="광고 이미지 (中文)" />
	                      <Tab label="광고 이미지 (英语)" />
	                      <Tab label="광고 이미지 (日本)" />
	                    </Tabs>
	                  </Paper>
	                  </FormControl>
	                  
	                    <SwipeableViews                      
	                      index={value}
	                      onChangeIndex={handleChangeCoverTab}
	                    >
	                      <TabPanel value={value} index={0} >
	                      
	                      
	                      
	                      <Table className={classes.bodyTable}>
	                          <TableHead>
	                              <TableRow key="_head_">
	                                  <TableCell align="center" style={{width: '10%'}}><Typography variant="subtitle2">번호</Typography></TableCell>
	                                  <TableCell align="center" style={{width: '30%'}}><Typography variant="subtitle2">썸네일URI</Typography></TableCell>
	                                  <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">Target_LINK</Typography></TableCell>
	                                  <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">언어</Typography></TableCell>
	                                  <TableCell align="center" style={{width: '15%'}}><Typography variant="subtitle2">삭제</Typography></TableCell>
	                              </TableRow>
	                          </TableHead>
	
	                          <TableBody>
	                              {imageChnList.slice().map((imageChn) => (
	                                  <TableRow key={'node-' + imageChn.ad_img_idx} >
	                                      <TableCell align="center" component="th" scope="row"><Typography variant="body2">{imageChn.ad_img_idx}</Typography></TableCell>
	                                      <TableCell align="center"><Typography variant="body2">{imageChn.thumbnail_domain+imageChn.thumbnail.name}</Typography></TableCell>
	                                      <TableCell align="center"><Typography variant="body2">{imageChn.link_addr}</Typography></TableCell>
	                                      <TableCell align="center"><Typography variant="body2">{imageChn.lang_cd}</Typography></TableCell>
	                                      <TableCell align="center"><Typography variant="body2"><IconButton aria-label="delete" fontSize="small" onClick={() => this.handleDelImageChnList(imageChn.ad_img_idx)}>
	                                      <DeleteIcon />
	                                      </IconButton></Typography></TableCell>
	                                  </TableRow>
	                              ))}
	                          </TableBody>
                          </Table>
                          
                          
	                      <Grid style={{display: 'flex'}}>
	                      <TextField className={classes.movieDetailHalf} value={newImageChn.thumbnail.name}  variant="outlined" size="small" label="" />
	                          <div className={classes.filebox}>
	                              <Button component="label" variant="contained" htmlFor="thumbnail" style={{borderRadius:'25px'}}>파일선택</Button>
	                              <input id="thumbnail" name="thumbnail" type="file" className={classes.fileSelection} onChange={(e) => this.handleChangeUploadImageFile(e,'CHN')} />
	                             
	                              </div>
	                              <TextField className={classes.movieDetailLink} value={newImageChn.link_addr} name="link_addr" onChange={(e) => this.handleChangeLinkAddr(e,value)} variant="outlined" size="small" label="Link Url" /> 
	                              <div className={classes.grow}></div>
	                              
	                              	<Button variant="contained" color="primary" endIcon={<AddIcon />} onClick={this.handleAddImageChnList}>추가</Button>
	                
	                        </Grid>
	                      
	                      
	                      </TabPanel>
	                      
	                      
	                      <TabPanel value={value} index={1} >
	                      
	                      

	                      
	                      
	                      
	                      </TabPanel>
	                      
	                      
	                      <TabPanel value={value} index={2} >

	                     
	                      
	                      
	                      
	                      
	                
	                      </TabPanel>
	                    </SwipeableViews>
	                    
	                    </form>
	                    
	                   
	                       
	                    
	                  
	                    
				       
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	    
	                  	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                    
	                  
	                  <FormControl className={classes.textField} component="fieldset">
		                  <TextField 
		                  id="outlined-multiline-static"
		                  label="광고설명"
		                  multiline
		                  rows={4}
		                  value={newAd.ad_desc} 
		                  name="ad_desc"
		                  variant="outlined"
		                	  onChange={this.handleChangeNewAd}
		                />
		              </FormControl>
		                  
		                  <FormControl className={classes.movieDetailHalf}>
					        <InputLabel id="demo-simple-select-label">광고주</InputLabel>
					        <Select
					          labelId="demo-simple-select-label"
					          id="advertiser"
					          name="advertiser"
					          value={newAd.advertiser}
					          onChange={this.handleChangeNewAd}
					        >
					          <MenuItem value={1}>광고주1</MenuItem>
					          <MenuItem value={2}>광고주2</MenuItem>
					          <MenuItem value={3}>광고주3</MenuItem>
					          
					        </Select>
					        
					       
					        </FormControl>
		                 

                  
                  </Paper>
                  
                </div>
                
               
                <Toolbar className={classes.toolbar}>
	                <div className={classes.delGrow}></div>
	                <Button className={classes.delGrow} variant="contained" color="notice" endIcon={<ClearIcon />} onClick={this.handleOpenDialog}>삭제</Button>
	                <div className={classes.grow}></div>
	                <Button variant="contained" color="primary" endIcon={<DoneIcon />} onClick={this.handleOpenDialog}>수정</Button>
	                <div>{'\u00A0'}</div>
	                {addAdResult === null ?   
	                <Button variant="contained" color="secondary" endIcon={<AddIcon />} onClick={this.handleInsertMovie}>추가</Button>
	                : <CircularProgress className={classes.CProgress} size={66}/>}
	            </Toolbar>
	            
                
            </Container>
        );
    }
};

export default withSnackbar(withRouter(withStyles(styles) (AdList)));