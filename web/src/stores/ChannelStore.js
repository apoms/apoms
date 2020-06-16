import {action, computed, flow, observable, toJS} from "mobx";
import axios from "axios";
import moment from "moment";
import _ from "lodash";

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

const EmptyChannel = {
		
		module_id: '',
		parent_module: '',
		channel_type: 2,
		has_selection: false,
		module_name: '',
		module_name_eng: '',
		module_name_jpn: '',
		show_type: 1,
		show_order: null,
		user_yn: true,
};


const EmptyMovie = {
		module_id: '',
		channel_id: '',
		mov_Id: 0,
		mov_name: '',
		mov_name_eng: '',
		mov_name_jpn: '',
		mins: '',
		play_cnt: '',
		play_cnt_str: '',
		down_cnt: '',
		has_down: false,			
		love_cnt: '',
		has_love: false,
		up_cnt: '',
		has_up: true,			
		diss_cnt: '',
		has_diss: true,
		mov_sn: '',
		mov_sn_ori: '',			
		mov_type: '',
		mov_provider: false,
		domain: '',
		f240p: '',			
		f360p: '',
		f480p: '',
		f720p: '',
		f1080p: '',
		mov_desc: '',
		mov_desc_eng: '',
		mov_desc_jpn: '',
		is_mosaic: '',
		mov_score: '',
		gift_total: '',
		show_yn: '',
		show_order: '',
		gmt_create_Id: '',
		gmt_create: '',
		mod_id: '',
		mod_dt: '',
		srch_mov_name: '',
		actorList: [],
		tagListChn: [],
		tagListEng: [],
		tagListJpn: [],
		coverImageChn: [{cover_domain: '' , horizontal_large: '' , horizontal_small: '' , vertical_large: '', vertical_small: '', lang_cd: '' }],
		coverImageEng: [],
		coverImageJpn: [],
};

const EmptyTag = {
		name: '',
};

const EmptyCoverChn = {
		cover_idx: 0,
		mov_id: '',
		cover_domain: '',
		horizontal_large: '',
		horizontal_small: '',
		vertical_large: '',
		vertical_small: '',
		lang_cd: 'CHN',
};

const EmptyCoverEng = {
		cover_idx: 0,
		mov_id: '',
		cover_domain: '',
		horizontal_large: '',
		horizontal_small: '',
		vertical_large: '',
		vertical_small: '',
		lang_cd: 'ENG',
};

const EmptyCoverJpn = {
		cover_idx: 0,
		mov_id: '',
		cover_domain: '',
		horizontal_large: '',
		horizontal_small: '',
		vertical_large: '',
		vertical_small: '',
		lang_cd: 'JPN',
};

const initState = {
        movieResult: null,
    };


export default class ChannelStore {
    @observable listState = ListState.Loading;
    @observable paging = {...EmptyPaging};
    @observable modules = [];
    @observable channels = [];
    @observable movies = [];
    @observable channelMovies = [];
    @observable allMovies = [];

    @observable dialogState = DialogState.Closed;
    @observable newChannel = {...EmptyChannel};
    @observable newMovie = {...EmptyMovie};
    
    @observable newTag = {...EmptyTag};
    
    @observable newCoverChn = {...EmptyCoverChn};
    @observable newCoverEng = {...EmptyCoverEng};    
    @observable newCoverJpn = {...EmptyCoverJpn};
    
    
   // @observable actorList = [];
   // @observable tagListChn = [{name:''}];
    //@observable tagListEng = [];
    //@observable tagListJpn = [];
        
    @observable movieResult = initState.movieResult;
    
    @observable searchWord ='';
    @observable setSearchWord ='';
    @observable value = 0;
    @action setValue = (value) => {
        this.value = value;
    };
   
    
    @action clearMovieStore = () => {
    	
    	this.modules = [];
    	this.channels = [];
    	this.movies = [];
    	this.channelMovies = [];
    	this.allMovies = [];
    	
    	this.newChannel = {...EmptyChannel};
    	this.newTag = {...EmptyTag};
    	this.newCoverChn = {...EmptyCoverChn};
    	this.newCoverEng = {...EmptyCoverEng};
    	this.newCoverJpn = {...EmptyCoverJpn};
        console.log('cleared create state : ' + this.newChannel.mov_name);
    }
    
    @action changemovieResult = (value) => {
        this.movieResult = value;
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
    //	alert(this.dialogState);
    //	alert(DialogState.Added);
        return this.dialogState === DialogState.Added;
    };

    @computed get isDialogOpen() {
        return this.dialogState !== DialogState.Closed;
    };

    @computed get isReadyAdding() {
        // return (this.newChannel.module_name.length > 0) && (this.newChannel.module_name.length > 0) && (this.newChannel.module_name.length > 0);
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

    
    @action changeNewChannel= (target, value) => {
        this.newChannel[target] = value;
    };
    
    @action changeNewCover= (target, value, langCd) => {
        
        if(langCd==="CHN"){
        	this.newCoverChn[target] = value;
        }else if(langCd==="ENG"){
        	this.newCoverEng[target] = value;     	
        }else if(langCd==="JPN"){
        	this.newCoverJpn[target] = value;
        }        
    };
   
    @action changeNewChannelActivated= (target, value) => {
        this.newChannel[target] = value;
    };
    
    
    @action changeChannelMovieList= (mov_id, removeTag) => {
    	// 매핑된 동영상 목록에서 선택시 channelMovies에서  removeTag true 이면 해당 mov_id를 제거함  false 추가
    	if(removeTag) {
    		this.channelMovies = this.channelMovies.filter(movie => movie.mov_id != mov_id);
    	} else {
    		this.channelMovies.push(this.newMovie);
    	}
    	
   // 	alert(this.channelMovies.length);
        };
    
    
    loadModules = flow(function* loadModules() {
        this.listState = ListState.Loading;
        
         const list = [{module_id:1, module_name:'国产', parent_module:1, channel_type:1, has_selection:true, show_type:1, show_order:1, user_yn:true },
        	 {module_id:2, module_name:'热点', parent_module:2, channel_type:1, has_selection:false, show_type:1, show_order:2, user_yn:true },
        	 {module_id:3, module_name:'日本AV', parent_module:3, channel_type:1, has_selection:true, show_type:1, show_order:3, user_yn:true },
        	 {module_id:4, module_name:'欧美', parent_module:4, channel_type:1, has_selection:true, show_type:1, show_order:4, user_yn:true },
        	 {module_id:5, module_name:'三级片', parent_module:5, channel_type:1, has_selection:false, show_type:1, show_order:5, user_yn:true },
        	 {module_id:6, module_name:'H动漫', parent_module:6, channel_type:1, has_selection:false, show_type:2, show_order:6, user_yn:true },
        	 {module_id:7, module_name:'深夜综艺', parent_module:7, channel_type:1, has_selection:false, show_type:2, show_order:7, user_yn:true },
   
];
        

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const modules = list;

            this.modules = modules;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.modules = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    
    
    
    loadChannelMovieList = flow(function* loadChannelMovieList() {
        this.listState = ListState.Loading;
        
         const list = [{mov_id:1001, mov_name:'기생충', mins:'01:45:00', mov_provider:true, show_yn:1 },
        	 {mov_id:1002, mov_name:'요가', mins:'01:45:00', mov_provider:false, show_yn:0 },
        	 {mov_id:1003, mov_name:'비행기', mins:'01:45:00', mov_provider:true, show_yn:1 }];
        
         const allList = [{mov_id:1001, mov_name:'침입자4', mins:'01:45:00', mov_provider:true, show_yn:1 },
        	 {mov_id:1002, mov_name:'착각', mins:'01:45:00', mov_provider:false, show_yn:0 },
        	 {mov_id:1003, mov_name:'프로야구', mins:'01:45:00', mov_provider:true, show_yn:1 }];
       

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const movies = list;
            const allMovies = allList;

            this.movies = movies;
            this.channelMovies = movies;
            //alert(this.channelMovies.length);
            this.allMovies = allMovies;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.movies = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    
    
    
    loadChannelList = flow(function* loadChannelList() {
        this.listState = ListState.Loading;
        

         const list =   [ 	{module_id:8, module_name:'国产口交', parent_module:1, channel_type:2, has_selection:true, show_type:1, show_order:1, user_yn:true },
        	        		{module_id:9, module_name:'国产拳交', parent_module:1, channel_type:2, has_selection:false, show_type:2, show_order:2, user_yn:false },
           	         
           	           ];

        try {
            // const response = yield axios.get('/api/v1/Movies');
           //  const Movies = response.data;
         	
        	const channels = list;

            this.channels = channels;
            
           // const newChannel = list;

           // this.newChannel = newChannel;
           // this.newCoverChn = list.coverChn;
           // this.newCoverEng = list.coverEng;
           // this.newCoverJpn = list.coverJpn;
         //   this.actorList = newChannel.actorList;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.modules = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadChannelDetail = flow(function* loadChannelDetail(module_id) {
      this.listState = ListState.Loading;

        try {
        	// by lee channels 에서 module_id에 해당하는 것을 찾아서  newChannel 에 저장 
        	const selectedChannel = _.find(toJS(this.channels), (channel) => channel.module_id == module_id);
            if(selectedChannel) {
            	const newChannel = selectedChannel;
	            this.newChannel = newChannel;
            }
           
         //   this.actorList = newMovie.actorList;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.movies = [];
            this.listState = ListState.LoadFailed;
            
        }
        
    });
  
    @action updateActorLimitCount = () => {
        let count = _.reduce(toJS(this.newMovies.actorList), (sum, n) => sum + n.groupUserCount, 0);
        count += this.newMovies.actorList.length;

       // this.channel.joinLimitCount = count;
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
    
    @action changeSelectedActor = (actor_idx) => {
    	const selectedGroup = _.find(toJS(this.actorList), (group) => group.actor_idx == actor_idx);
        if(selectedGroup) {
            this.newChannel.actorList.push(selectedGroup);
        }
       // this.updateJoinLimitCount();
    }
    
    @action changeTagChn = (langCd, tag) => {

    	// const result = this.newChannel.tagListChn.map((tag) => tag.name); 
    	// const selectedGroup = _.find(toJS(this.tagListChn), (tag) => tag.name === tag);
    	if ( langCd === 'CHN') {
	    	let matchIndex = -1;
	    	for(let i=0; i<this.newChannel.tagListChn.length; i++) {
	            if(this.newChannel.tagListChn[i].name === tag) {
	            	matchIndex = i;
	                break;
	            }
	        }
	    	 if(matchIndex < 0) {
	     		const selectTag = {name : tag, lang_cd : 'CHN'};
	     		this.newChannel.tagListChn.push(selectTag);
	         }
    	} else if ( langCd === 'ENG') { 
    		let matchIndex = -1;
        	for(let i=0; i<this.newChannel.tagListEng.length; i++) {
                if(this.newChannel.tagListEng[i].name === tag) {
                	matchIndex = i;
                    break;
                }
            }
        	 if(matchIndex < 0) {
         		const selectTag = {name : tag, lang_cd : 'ENG'};
         		this.newChannel.tagListEng.push(selectTag);
             }
    	} else if ( langCd === 'JPN') { 
    		let matchIndex = -1;
        	for(let i=0; i<this.newChannel.tagListJpn.length; i++) {
                if(this.newChannel.tagListJpn[i].name === tag) {
                	matchIndex = i;
                    break;
                }
            }
        	 if(matchIndex < 0) {
         		const selectTag = {name : tag, lang_cd : 'JPN'};
         		this.newChannel.tagListJpn.push(selectTag);
             }
    	}
    	
    }
    
    @action removeActor = (actor_idx) => {
    	 for(let i=0; i<this.newChannel.actorList.length; i++) {
             if(this.newChannel.actorList[i].actor_idx === actor_idx) {
                 this.newChannel.actorList.splice(i, 1);
                 break;
             }
         }
    }
    
	 @action removeTag = (langCd,tag) => {	
		 if ( langCd === 'CHN') {
		   	 for(let i=0; i<this.newChannel.tagListChn.length; i++) {
		            if(this.newChannel.tagListChn[i].name === tag) {
		                this.newChannel.tagListChn.splice(i, 1);
		                break;
		            }
		        }
	   	 }else if ( langCd === 'ENG') {
	   		 for(let i=0; i<this.newChannel.tagListEng.length; i++) {
		            if(this.newChannel.tagListEng[i].name === tag) {
		                this.newChannel.tagListEng.splice(i, 1);
		                break;
		            }
		        }
	   		 
	   	 }else if ( langCd === 'JPN') {
	   		for(let i=0; i<this.newChannel.tagListJpn.length; i++) {
	            if(this.newChannel.tagListJpn[i].name === tag) {
	                this.newChannel.tagListJpn.splice(i, 1);
	                break;
	            }
	        }
	   		 
	   	 }
	 }
    

    addNewChannel = flow(function* addNewChannel() {
    	
    	this.dialogState = DialogState.Adding;
        try {
            const param = this.newChannel;
            
           //  yield axios.post('/api/v1/servers', param);
           //  const response = yield axios.get('/api/v1/servers');
           //  const channels = response.data;
             
            const channels = this.channels;
             this.newChannel = {...EmptyChannel};
             this.channels = channels;

        //  alert("addNewChannel ok ");
            this.dialogState = DialogState.Added;
        } catch(error) {
            this.dialogState = DialogState.AddFailed;
        }
    });
    
    uploadFile = flow(function* uploadFile(filetype, filename, uploadFile) {
    	this.fileDialogState = FileDialogState.Uploading;
        this.listState = ListState.Loading;
        this.movieResult = "pending";
        try {
        	 const param = new FormData();
             param.append('filetype', filetype);
             param.append('filename', filename);
             param.append('uploadFile', uploadFile);

       // yield axios.post('/api/v1/files/channel-attach', param);
       // yield axios.post('https://nms.aetherdemo.tk:3001/media/upload', param).then(res => {    
            yield axios.post('http://118.67.170.98:3000/media/upload', param).then(res => {
                 // alert('동영상 및 커버 이미지 업로드 성공');
              }).catch(err => {
                alert(err);
              });
            
           
            
            this.listState = ListState.Loaded;
            this.upload.comment = '';
            this.upload.file = '';

            // const response = yield axios.get(`/api/v1/channels/files?channel-id=${this.channelId}`);
            // const fileList = _.sortBy(response.data, ['fileId']);
            this.movieResult = null;
            //this.fileList = fileList;
            //this.paging.totalCount = fileList.length;
            this.fileDialogState = FileDialogState.Uploaded;
        } catch(error) {
            this.fileDialogState = FileDialogState.UploadFailed;
        }
    });
    
    removeChannelMovie = flow(function* removeChannelMovie() {
    	//toJS(this.newMovie.actorList).map((item) => item.actor_idx)
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.name));
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.lang_cd));
    	// this.movieResult = "pending";
       this.dialogState = DialogState.Adding;
        
        const list = [{mov_id:1001, mov_name:'기생충', mins:'01:45:00', mov_provider:true, show_yn:1 },
       	 
       	 {mov_id:1003, mov_name:'비행기', mins:'01:45:00', mov_provider:true, show_yn:1 }];
        
        const allList = [{mov_id:1001, mov_name:'침입자4', mins:'01:45:00', mov_provider:true, show_yn:1 },
       	 {mov_id:1002, mov_name:'착각', mins:'01:45:00', mov_provider:false, show_yn:0 },
       	 {mov_id:1002, mov_name:'요가', mins:'01:45:00', mov_provider:false, show_yn:0 },
       	 {mov_id:1003, mov_name:'프로야구', mins:'01:45:00', mov_provider:true, show_yn:1 }];
     
 

        try {
            const param = this.channelMovies;
           
          //  yield axios.post('/api/v1/servers', param);
           // const response = yield axios.get('/api/v1/servers');
            // const movies = response.data;
          
            this.movies = list;
            this.channelMovies = list;
            //alert(this.channelMovies.length);
            this.allMovies = allList;
           
            this.dialogState = DialogState.Added;
        } catch(error) {
            this.dialogState = DialogState.AddFailed;
        }
    });
    
    
    getActorList = flow(function* getActorList() {
    	const list = [{actor_idx: 1, actor_name: '정우성' },
            { actor_idx: 2, actor_name: '송강호' },
            { actor_idx: 3, actor_name: '김향기' },
           ];
        try {
            const params = {
                "user-id": "",
                paging: "no"
            };

          //  const response = yield axios.get("/api/v1/groups", {params: params});
            const actorList = list; // response.data;
            this.actorList = actorList;
        } catch(error) {
            this.actorList = [];
        }
    })
}