import {action, computed, flow, observable} from "mobx";
import axios from "axios";
import _ from "lodash";
import {toJS} from "mobx";

export const ListState = {
    Loading: 'Loading',
    Loaded: 'Loaded',
    LoadFailed: 'LoadFailed',
};

const FileDialogState = {
	    Closed: 'Closed',
	    Loading: 'Loading',
	    Loaded: 'Loaded',
	    LoadFailed: 'LoadFailed',
	    Uploading: 'Uploading',
	    Uploaded: 'Uploaded',
	    UploadFailed: 'UploadFailed',
	};

export const DialogState = {
    Closed: 'Closed',
    Opened: 'Opened',
    Adding: 'Adding',
    Added: 'Added',
    AddFailed: 'AddFailed',
};

const EmptyPaging = {
    page: 0,
    rowsPerPage: 10,
};

const EmptyShowCtg = {
		show_location: '0',
		show_location_name: '',
		
};

const EmptyAd = {
		ad_id : 0,
        ad_name: '',
        ad_show_time: 0,
        ad_skip_time: 0,
        ad_weight: 0,
        channel_type: '',
        link_type: 0,
        module_id: 0, 
        status: false,
        show_order: null, 
        ad_start_dt: '',
        ad_stop_dt: '',
        ad_desc: '', 
        advertiser: 0
};


const EmptyImageChn = {
	ad_img_idx: 0,
	ad_id: '',
	thumbnail_domain: '',
	thumbnail: '',
	link_addr: '',
	lang_cd: 'CHN',	
};

const EmptyImageEng = {
		ad_img_idx: 0,
		ad_id: '',
		thumbnail_domain: '',
		thumbnail: '',
		link_addr: '',
		lang_cd: 'ENG',	
	};

const EmptyImageJpn = {
		ad_img_idx: 0,
		ad_id: '',
		thumbnail_domain: '',
		thumbnail: '',
		link_addr: '',
		lang_cd: 'JPN',	
	};

const initState = {
        addAdResult: null,
    };


export default class AdLiveStore {
    @observable listState = ListState.Loading;
    
    @observable paging = {...EmptyPaging};
    @observable value = 0;
    @action setValue = (value) => {
        this.value = value;
    };

    @observable dialogState = DialogState.Closed;
    @observable advertisements = [];

    @observable imageChnList = [];
    @observable imageEngList = [];
    @observable imageJpnList = [];
     
    @observable newShowCtg = {...EmptyShowCtg};
    @observable newAd = {...EmptyAd};
    @observable newImageChn = {...EmptyImageChn};
    @observable newImageEng = {...EmptyImageEng};
    @observable newImageJpn = {...EmptyImageJpn};
    
    @observable addAdResult = initState.addAdResult;
    
    @action clearAdStore = () => {
    	this.advertisements = [];
    	this.imageChnList = [];
    	this.imageEngList = [];
    	this.imageJpnList = [];
    	this.newShowCtg = {...EmptyShowCtg};
    	this.newAd = {...EmptyAd};
    	this.newImageChn = {...EmptyImageChn};
    	this.newImageEng = {...EmptyImageEng};
    	this.newImageJpn = {...EmptyImageJpn};
    	
    }
    
    @action changeAdResult = (value) => {
        this.addAdResult = value;
    };
   
    @computed get isLoading() {
        return this.listState === ListState.Loading;
    };

    @computed get isLoadFailed() {
        return this.listState === ListState.LoadFailed;
    };

    @computed get isAdding() {
        return this.dialogState === DialogState.Adding;
    };

    @computed get isAddFailed() {
        return this.dialogState === DialogState.AddFailed;
    };

    @computed get isAdded() {
        return this.dialogState === DialogState.Added;
    };

    @computed get isDialogOpen() {
        return this.dialogState !== DialogState.Closed;
    };

    @computed get isReadyAdding() {
        //return (this.newAd.name.length > 0) && (this.newAd.broadcastUrl.length > 0) && (this.newAd.playbackUrl.length > 0);
    };

    @action clearListState = () => {
        this.listState = ListState.Loaded;
    };

    @action changePagingPage = (page) => {
        this.paging.page = page;
    };

    @action changePagingRowsPerPage = (rowsPerPage) => {
        this.paging.rowsPerPage = rowsPerPage;
    };

    @action clearDialogState = (open) => {
        if(open) {
            this.dialogState = DialogState.Opened;
        } else {
            this.dialogState = DialogState.Closed;
        }
    };

    @action openDialog = () => {
        this.dialogState = DialogState.Opened;
    };

    @action closeDialog = () => {
        this.dialogState = DialogState.Closed;
    };

    @action changeNewMoviePriority = (priority) => {
        this.newMovie.priority = priority;
    }
    
    @action changeNewShowCtg= (target, value) => {
        this.newShowCtg[target] = value;
    };
    
    @action changeNewAd= (target, value) => {
    	this.newAd[target] = value;
    };
       
    @action changeNewImage= (target, value) => {
        this.newImage[target] = value;
    };
   
    @action changeNewAdActivated= (target, value) => {
        this.newAd[target] = value;
    };
    
    @action changeNewCover= (target, value, langCd) => {
        
    //	alert(target+value+langCd);
    	
       if(langCd==="CHN"){
        	this.newImageChn[target] = value;
        }else if(langCd==="ENG"){
        	this.newImageEng[target] = value;     	
        }else if(langCd==="JPN"){
        	this.newImageJpn[target] = value;
        }        
    };
    
    @action changeLinkAddr= (target, value, langCd) => {
    	this.newImageChn[target] = value;
    }
    @action addImageChnList= () => {
    	const rowCnt = this.imageChnList.length;
    	this.newImageChn.ad_img_idx = rowCnt+1;
    	this.imageChnList.push(this.newImageChn);
    	this.newImageChn = {...EmptyImageChn};
    }
    
    @action delImageChnList= (ad_img_idx) => {
    	this.imageChnList  = this.imageChnList.filter(num => num.ad_img_idx !== ad_img_idx);
    }
    
    @computed get filteredActorList() {
        const actorList = toJS(this.newChannel.actorList);

        const filteredActorList = _.filter(toJS(this.actorList), (group) => {
            const groupInInviteList = _.find(actorList, (inviteGroup) => inviteGroup.actor_idx === group.actor_idx);
            if(groupInInviteList) {
               return false;
            } else {
                return true;
            }
        });

        return filteredActorList;
    }
    
    
    
    loadAds = flow(function* loadAds() {
        this.listState = ListState.Loading;
        
         const list = [
        	 {ad_id:1001, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1002, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 {ad_id:1003, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1004, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 {ad_id:1005, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1006, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 {ad_id:1007, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1008, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 {ad_id:1009, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1010, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 {ad_id:1011, ad_name:'반도체광고', status:1, advertiser:"삼성전자" },
        	 {ad_id:1012, ad_name:'디오스', status:0, advertiser:"LG전자" },
        	 ];
        

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const advertisements = list;

            this.advertisements = advertisements;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.advertisements = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadAdDetail = flow(function* loadAdDetail() {
        this.listState = ListState.Loading;
        
        
        
        
         const list = {
        		    ad_id : 11,
        	        ad_name: '반도체2',
        	        ad_show_time: 10,
        	        ad_skip_time: 5,
        	        ad_weight: 0,
        	        channel_type: '',
        	        link_type: '',
        	        module_id: '', 
        	        status: true,
        	        show_order: '', 
        	        ad_start_dt: '',
        	        ad_stop_dt: '',
        	        ad_desc: '반도체 광고 좋아요', 
        	        advertiser: 0,
        	        thumbnail_chn : [
        	        	 {ad_img_idx:1001, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/1.jpg", link_addr:"http://", lang_cd:"CHN" },
        	        	 {ad_img_idx:1002, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/2.jpg", link_addr:"http://", lang_cd:"CHN" },
        	        	 {ad_img_idx:1003, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/3.jpg", link_addr:"http://", lang_cd:"CHN" },
        	        	 ],
    	        	 thumbnail_eng : [
        	        	 {ad_img_idx:1004, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/1.jpg", link_addr:"http://", lang_cd:"ENG" },
        	        	 {ad_img_idx:1005, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/2.jpg", link_addr:"http://", lang_cd:"ENG" },
        	        	 {ad_img_idx:1006, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/3.jpg", link_addr:"http://", lang_cd:"ENG" },
        	        	 ],
			         thumbnail_eng : [
			        	 {ad_img_idx:1007, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/1.jpg", link_addr:"http://", lang_cd:"JPN" },
			        	 {ad_img_idx:1008, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/2.jpg", link_addr:"http://", lang_cd:"JPN" },
			        	 {ad_img_idx:1009, ad_id:11, thumbnail_domain:"http://thumbnail.", thumbnail: "/ad/3.jpg", link_addr:"http://", lang_cd:"JPN" },
			        	 ],
        	        
        	};

        try {
            // const response = yield axios.get('/api/v1/Movies');
           //  const Movies = response.data;
            
            const newAd= list;

            this.newAd = newAd;
            this.imageChnList = newAd.thumbnail_chn;
            
            this.listState = ListState.Loaded;
        } catch(error) {
            this.advertisements = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    

    addNewMovie = flow(function* addNewMovie() {
        this.dialogState = DialogState.Adding;

        try {
            const param = this.newMovie;
          //  yield axios.post('/api/v1/advertisements', param);
          //  const response = yield axios.get('/api/v1/advertisements');
           // const advertisements = response.data;

           // this.newMovie = {...EmptyMovie};
           // this.advertisements = advertisements;
            
            const move_id = '1';
            this.uploadFile(move_id);
            
            this.dialogState = DialogState.Added;
        } catch(error) {
            this.dialogState = DialogState.AddFailed;
        }
    });
    
    uploadFile = flow(function* uploadFile(move_id) {

        this.fileDialogState = FileDialogState.Uploading;
        this.listState = ListState.Loading;
        this.addAdResult = "pending";
        try {
             const param = new FormData();
             param.append('filetype', 'image');
             param.append('filename', move_id+'_ad');
             param.append('uploadFile', this.newMovie.f240p);
        
       // yield axios.post('/api/v1/files/channel-attach', param);
       // yield axios.post('https://nms.aetherdemo.tk:3001/media/upload', param).then(res => {    
            yield axios.post('http://118.67.170.98:3000/media/upload', param).then(res => {
                alert('광고 등록 성공');
              }).catch(err => {
                alert(err);
              });
            
            this.addAdResult = null;
            
            this.listState = ListState.Loaded;
            this.upload.comment = '';
            this.upload.file = '';

            // const response = yield axios.get(`/api/v1/channels/files?channel-id=${this.channelId}`);
            // const fileList = _.sortBy(response.data, ['fileId']);

            //this.fileList = fileList;
            //this.paging.totalCount = fileList.length;
            this.fileDialogState = FileDialogState.Uploaded;
        } catch(error) {
            this.fileDialogState = FileDialogState.UploadFailed;
        }
    });
}