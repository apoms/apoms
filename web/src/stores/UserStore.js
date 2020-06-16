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


export default class UserStore {
    @observable listState = ListState.Loading;
    @observable paging = {...EmptyPaging};
    @observable users = [];
    @observable actors = [];

    @observable dialogState = DialogState.Closed;
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
    	this.users = [];
    	this.actors = [];
    	this.newMovie = {...EmptyMovie};
    	this.newTag = {...EmptyTag};
    	this.newCoverChn = {...EmptyCoverChn};
    	this.newCoverEng = {...EmptyCoverEng};
    	this.newCoverJpn = {...EmptyCoverJpn};
        console.log('cleared create state : ' + this.newMovie.mov_name);
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
        return this.dialogState === DialogState.Added;
    };

    @computed get isDialogOpen() {
        return this.dialogState !== DialogState.Closed;
    };

    @computed get isReadyAdding() {
        return (this.newMovie.name.length > 0) && (this.newMovie.broadcastUrl.length > 0) && (this.newMovie.playbackUrl.length > 0);
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

    
    @action changeNewMovie= (target, value) => {
        this.newMovie[target] = value;
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
   
    @action changeNewMovieActivated= (target, value) => {
        this.newMovie[target] = value;
    };
    
    loadMovies = flow(function* loadMovies() {
        this.listState = ListState.Loading;
        
         const list = [{actor_idx:1001, actor_name:'金杨', videos_count:213, star_level:89.01, photo_url:'/images/vj1.png' },
        	 {actor_idx:1002, actor_name:'Actor Lee', videos_count:3567, star_level:95.32, photo_url:'/images/vj2.png'  },
        	 ];
        

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const users = list;

            this.actors = users;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.actors = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadUsers = flow(function* loadUsers() {
        this.listState = ListState.Loading;
       
        const list = [{mov_id:1001, mov_name:'VJ Kim', mins:'01:45:00', mov_provider:true, show_yn:1 },
       	 {mov_id:1002, mov_name:'VJ Lee', mins:'01:45:00', mov_provider:false, show_yn:0 },
       	 {mov_id:1003, mov_name:'VJ Choi', mins:'01:45:00', mov_provider:true, show_yn:1 }];
     
        

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const users = list;

            this.users = users;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.actors = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadMovieDetail = flow(function* loadMovieDetail() {
        this.listState = ListState.Loading;
        

         const list = {
        			module_id: '',
        			channel_id: '',
        			mov_Id: 1001,
        			mov_name: '金杨',
        			mov_name_eng: 'Kim Yang',
        			mov_name_jpn: 'キムさん',
        			mins: '01:45:00',
        			photo_url: '/images/vj1.png',
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
        			domain: 'https://www.aetherdemo.tk:8002/',
        			f240p: '기생충 감독판_240p.mp4',			
        			f360p: '기생충 감독판_360p.mp4',
        			f480p: '기생충 감독판_480p.mp4',
        			f720p: '기생충 감독판_720p.mp4',
        			f1080p: '기생충 감독판_1080.mp4',
        			mov_desc: '金养恩是年轻人中最受欢迎的VJ女星',
        			mov_desc_eng: 'Kim Yang-eun is a popular VJ actress among young people',
        			mov_desc_jpn: 'キムさんは若年層に人気のVJ俳優',
        			is_mosaic: '',
        			mov_score: '',
        			gift_total: '',
        			show_yn: 0,
        			show_order: '',
        			gmt_create_Id: '',
        			gmt_create: '',
        			mod_id: '',
        			mod_dt: '',
        			srch_mov_name: '',
        			actorList: [ { actor_idx: 2, actor_name: '송강호' },
        	            { actor_idx: 3, actor_name: '김향기' },
        	           ],
        	        tagListChn: [ 	{id: 1 , mov_id: 1 , name: '好玩' , lang_cd: 'CHN' },
        	        				{id: 2 , mov_id: 1 , name: '利益' , lang_cd: 'CHN' }
           	         
           	           ],
           	        tagListEng: [ 	{id: 3 , mov_id: 1 , name: 'fun' , lang_cd: 'ENG' },
           	        				{id: 4 , mov_id: 1 , name: 'interest' , lang_cd: 'ENG' }
	         
           	        ],
           	        
           	        tagListJpn: [ 	{id: 5 , mov_id: 1 , name: '楽しい' , lang_cd: 'JPN' },
           	        				{id: 6 , mov_id: 1 , name: '興味' , lang_cd: 'JPN' }

           	        ],
           	        
           	        
           	     coverChn:  
           	     {cover_idx: 1 , mov_id: 1 , cover_domain: 'http://cover..' , horizontal_large: 'hl_chn' , horizontal_small: 'hs' , vertical_large: 'vl' , vertical_small: 'vs' , lang_cd: 'CHN' },
        	     coverEng:  
           	     {cover_idx: 2 , mov_id: 1 , cover_domain: 'http://cover..' , horizontal_large: 'hl_eng' , horizontal_small: 'hs' , vertical_large: 'vl' , vertical_small: 'vs' , lang_cd: 'ENG' },
        	     coverJpn:  
           	     {cover_idx: 3 , mov_id: 1 , cover_domain: 'http://cover..' , horizontal_large: 'hl_jpn' , horizontal_small: 'hs' , vertical_large: 'vl' , vertical_small: 'vs' , lang_cd: 'JPN' },
        
        	};

        try {
            // const response = yield axios.get('/api/v1/Movies');
           //  const Movies = response.data;
            
            const newMovie = list;

            this.newMovie = newMovie;
            this.newCoverChn = list.coverChn;
            this.newCoverEng = list.coverEng;
            this.newCoverJpn = list.coverJpn;
         //   this.actorList = newMovie.actorList;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.actors = [];
            this.users = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
  
    @action updateActorLimitCount = () => {
        let count = _.reduce(toJS(this.newMovies.actorList), (sum, n) => sum + n.groupUserCount, 0);
        count += this.newMovies.actorList.length;

       // this.channel.joinLimitCount = count;
    }
    
    @computed get filteredActorList() {
        const actorList = toJS(this.newMovie.actorList);

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
            this.newMovie.actorList.push(selectedGroup);
        }
       // this.updateJoinLimitCount();
    }
    
    @action changeTagChn = (langCd, tag) => {

    	// const result = this.newMovie.tagListChn.map((tag) => tag.name); 
    	// const selectedGroup = _.find(toJS(this.tagListChn), (tag) => tag.name === tag);
    	if ( langCd === 'CHN') {
	    	let matchIndex = -1;
	    	for(let i=0; i<this.newMovie.tagListChn.length; i++) {
	            if(this.newMovie.tagListChn[i].name === tag) {
	            	matchIndex = i;
	                break;
	            }
	        }
	    	 if(matchIndex < 0) {
	     		const selectTag = {name : tag, lang_cd : 'CHN'};
	     		this.newMovie.tagListChn.push(selectTag);
	         }
    	} else if ( langCd === 'ENG') { 
    		let matchIndex = -1;
        	for(let i=0; i<this.newMovie.tagListEng.length; i++) {
                if(this.newMovie.tagListEng[i].name === tag) {
                	matchIndex = i;
                    break;
                }
            }
        	 if(matchIndex < 0) {
         		const selectTag = {name : tag, lang_cd : 'ENG'};
         		this.newMovie.tagListEng.push(selectTag);
             }
    	} else if ( langCd === 'JPN') { 
    		let matchIndex = -1;
        	for(let i=0; i<this.newMovie.tagListJpn.length; i++) {
                if(this.newMovie.tagListJpn[i].name === tag) {
                	matchIndex = i;
                    break;
                }
            }
        	 if(matchIndex < 0) {
         		const selectTag = {name : tag, lang_cd : 'JPN'};
         		this.newMovie.tagListJpn.push(selectTag);
             }
    	}
    	
    }
    
    @action removeActor = (actor_idx) => {
    	 for(let i=0; i<this.newMovie.actorList.length; i++) {
             if(this.newMovie.actorList[i].actor_idx === actor_idx) {
                 this.newMovie.actorList.splice(i, 1);
                 break;
             }
         }
    }
    
	 @action removeTag = (langCd,tag) => {	
		 if ( langCd === 'CHN') {
		   	 for(let i=0; i<this.newMovie.tagListChn.length; i++) {
		            if(this.newMovie.tagListChn[i].name === tag) {
		                this.newMovie.tagListChn.splice(i, 1);
		                break;
		            }
		        }
	   	 }else if ( langCd === 'ENG') {
	   		 for(let i=0; i<this.newMovie.tagListEng.length; i++) {
		            if(this.newMovie.tagListEng[i].name === tag) {
		                this.newMovie.tagListEng.splice(i, 1);
		                break;
		            }
		        }
	   		 
	   	 }else if ( langCd === 'JPN') {
	   		for(let i=0; i<this.newMovie.tagListJpn.length; i++) {
	            if(this.newMovie.tagListJpn[i].name === tag) {
	                this.newMovie.tagListJpn.splice(i, 1);
	                break;
	            }
	        }
	   		 
	   	 }
	 }
    

    addNewMovie = flow(function* addNewMovie() {
    	//toJS(this.newMovie.actorList).map((item) => item.actor_idx)
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.name));
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.lang_cd));
    	// this.movieResult = "pending";
        this.dialogState = DialogState.Adding;

        try {
            const param = this.newMovie;
            
            // 1. insert moive_bas , get mov_id          
            // 3. insert movie_cover
            // 4. insert movie_actor_rel 
            // 5. insert movie_tag_txn 
            
            // mov_id
            
            
            // 2. video, image uploadFile
            const move_id = '1';
            const filepath = move_id;
            const filename ='';
            
            // cover image 파일 중,영,일 4개씩 
            //*       
	        const fileTypeImg = 'image';
	        // 중
	        if( this.newCoverChn.horizontal_large ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_chn'), this.newCoverChn.horizontal_large);
           	}
               if( this.newCoverChn.horizontal_small ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_chn'), this.newCoverChn.horizontal_small);
           	}
               if( this.newCoverChn.vertical_large ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_chn'), this.newCoverChn.vertical_large);
           	}
               
               if( this.newCoverChn.vertical_small ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_chn'), this.newCoverChn.vertical_small);
           	}
           // 영    
           if( this.newCoverEng.horizontal_large ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_eng'), this.newCoverEng.horizontal_large);
          	}
              if( this.newCoverEng.horizontal_small ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_eng'), this.newCoverEng.horizontal_small);
          	}
              if( this.newCoverEng.vertical_large ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_eng'), this.newCoverEng.vertical_large);
          	}
              
              if( this.newCoverEng.vertical_small ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_eng'), this.newCoverEng.vertical_small);
          	}
              //일
          if( this.newCoverJpn.horizontal_large ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_jpn'), this.newCoverJpn.horizontal_large);
        	}
            if( this.newCoverJpn.horizontal_small ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_jpn'), this.newCoverJpn.horizontal_small);
        	}
            if( this.newCoverJpn.vertical_large ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_jpn'), this.newCoverJpn.vertical_large);
        	}
            
            if( this.newCoverJpn.vertical_small ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_jpn'), this.newCoverJpn.vertical_small);
        	}
                  
              //  */   
            
            const filetype = 'video';
            
            // video 파일 5개            
            
            if( this.newMovie.f480p ){
            	this.uploadFile(filetype, filename.concat(filepath,'_480P'), this.newMovie.f480p);
        	}else{ alert("f480p 파일선택 하세요."); this.movieResult = null; return;}
            
            
            if( this.newMovie.f240p ){
            	this.uploadFile(filetype, filename.concat(filepath,'_240P'), this.newMovie.f240p);
         	}
            
            if( this.newMovie.f360p ){
            	this.uploadFile(filetype, filename.concat(filepath,'_360P'), this.newMovie.f360p);
        	}
            
            if( this.newMovie.f720p ){
            	this.uploadFile(filetype, filename.concat(filepath,'_720P'), this.newMovie.f720p);
        	}
            if( this.newMovie.f1080p ){
            	this.uploadFile(filetype, filename.concat(filepath,'_1080P'), this.newMovie.f1080p);
        	}
         
       //     alert('동영상 및 커버 이미지 업로드 완료');
            this.movieResult = null;
            
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