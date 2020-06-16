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
		moduleId: '',
		channelId: '',
		movId: 0,
		movName: '',
		movNameEng: '',
		movNameJpn: '',
		mins: '',
		playCnt: '',
		playCntStr: '',
		downCnt: '',
		hasDown: false,			
		loveCnt: '',
		hasLove: false,
		upCnt: '',
		hasUp: true,			
		dissCnt: '',
		hasDiss: true,
		movSn: '',
		movSnOri: '',			
		movType: '',
		movProvider: false,
		domain: '',
		f240p: '',			
		f360p: '',
		f480p: '',
		f720p: '',
		f1080p: '',
		movDesc: '',
		movDescEng: '',
		movDescJpn: '',
		isMosaic: '',
		movScore: '',
		giftTotal: '',
		showYn: '',
		showOrder: '',
		gmtCreateId: '',
		gmtCreate: '',
		modId: '',
		modDt: '',
		srchMovName: '',
		actorList: [],
		tagListChn: [],
		tagListEng: [],
		tagListJpn: [],
		
		coverChn: '',
		coverEng: '',
		coverJpn: '',
		
		
		coverChnFile: '',
		coverEngFile: '',
		coverEngFile: '',
};

const EmptyTag = {
		name: '',
};

const EmptyCoverChn = {
		coverIdx: 0,
		movId: '',
		coverDomain: '',
		horizontalLarge: '',
		horizontalSmall: '',
		verticalLarge: '',
		verticalSmall: '',
		langCd: 'CHN',
};

const EmptyCoverEng = {
		coverIdx: 0,
		movId: '',
		coverDomain: '',
		horizontalLarge: '',
		horizontalSmall: '',
		verticalLarge: '',
		verticalSmall: '',
		langCd: 'ENG',
};

const EmptyCoverJpn = {
		coverIdx: 0,
		movId: '',
		coverDomain: '',
		horizontalLarge: '',
		horizontalSmall: '',
		verticalLarge: '',
		verticalSmall: '',
		langCd: 'JPN',
};

const initState = {
        movieResult: null,
    };


export default class MovieStore {
    @observable listState = ListState.Loading;
    @observable paging = {...EmptyPaging};
    
    @observable defaultchannels = [];
    @observable movies = [];
    
    @observable movieFiles =  [];

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
    @observable coverDomain ='';
    @observable setSearchWord ='';
    @observable value = 0;
    @action setValue = (value) => {
        this.value = value;
    };
        
    @action clearMovieStore = () => {
    	this.movies = [];
    	this.movieFiles = [];
    	this.newMovie = {...EmptyMovie};
    	this.newTag = {...EmptyTag};
    	this.newCoverChn = {...EmptyCoverChn};
    	this.newCoverEng = {...EmptyCoverEng};
    	this.newCoverJpn = {...EmptyCoverJpn};
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
    
    @action changeNewMovieFile= (target, file) => {
    	
    //	alert(target+file+file.name);
    	
        this.newMovie[target] = file.name;  
    
        let matchIndex = -1;
    	for(let i=0; i<this.movieFiles.length; i++) {
    		alert(this.movieFiles[i].name+"===="+file.name);
            if(this.movieFiles[i].name === file.name) {
            	matchIndex = i;
                break;
            }
        }
    	 if(matchIndex < 0) {
    		 alert(file.name+"추가함");
     		this.movieFiles.push(file);
         }else{
        	 alert(file.name+"존재함");
         }
        
     //   this.newMovie.movieFiles[target] = file; // fileObject 담
        
        
    };
    
    @action changeCoverDomain= (target, value) => {
    	this.coverDomain = value;
    	this.newCoverChn[target] = value;
    	this.newCoverEng[target] = value;
    	this.newCoverJpn[target] = value;
    };
    
    
    @action changeNewCover= (target, file, langCd) => {
        
        if(langCd==="CHN"){
        	this.newCoverChn[target] = file.name;
        }else if(langCd==="ENG"){
        	this.newCoverEng[target] = file.name;     	
        }else if(langCd==="JPN"){
        	this.newCoverJpn[target] = file.name;
        }  
        
       // this.newMovie.coverChnFile[target] = value;
        
    };
   
    @action changeNewMovieActivated= (target, value) => {
        this.newMovie[target] = value;
    };
    
    
    getDefaultChannelList = flow(function* getDefaultChannelList(langCode) {
        this.listState = ListState.Loading;
   // alert(langCode);
        try {
        	
        	const param = langCode;
        	//const response = yield axios.post(`/home/defaultchannels`, param);
           // const response = yield axios.get(`/home/defaultchannels?lang-code=CHN`);
           const response = yield axios.get('/home/defaultchannels?lang-code=${langCode}');
            // response = yield axios.get(`/api/v1/home/defaultchannels?lang-code=${langCode}`);
            const list = response.data;
           
            this.defaultchannels = list;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.defaultchannels = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadMovies = flow(function* loadMovies() {
        this.listState = ListState.Loading;
        
         const list = [{movId:1001, movName:'기생충', mins:'01:45:00', movProvider:true, showYn:1 },
        	 {movId:1002, movName:'기생충2', mins:'01:45:00', movProvider:false, showYn:0 },
        	 {movId:1003, movName:'기생충3', mins:'01:45:00', movProvider:true, showYn:1 }];
        

        try {
            // const response = yield axios.get('/api/v1/Movies');
            // const list = response.data;
            
            const movies = list;

            this.movies = movies;
            this.listState = ListState.Loaded;
        } catch(error) {
            this.movies = [];
            this.listState = ListState.LoadFailed;
            
        }
    });
    
    loadMovieDetail = flow(function* loadMovieDetail() {
        this.listState = ListState.Loading;
        

         const list = {
        			moduleId: '',
        			channelId: '',
        			movId: 1001,
        			movName: '蠕虫',
        			movNameEng: 'helminth',
        			movNameJpn: '寄生虫',
        			mins: '01:45:00',
        			playCnt: '',
        			playCntStr: '',
        			downCnt: '',
        			hasDown: false,			
        			loveCnt: '',
        			hasLove: false,
        			upCnt: '',
        			hasUp: true,			
        			dissCnt: '',
        			hasDiss: true,
        			movSn: '',
        			movSnOri: '',			
        			movType: '',
        			movProvider: false,
        			domain: 'https://www.aetherdemo.tk:8002/',
        			f240p: '기생충 감독판_240p.mp4',			
        			f360p: '기생충 감독판_360p.mp4',
        			f480p: '기생충 감독판_480p.mp4',
        			f720p: '기생충 감독판_720p.mp4',
        			f1080p: '기생충 감독판_1080.mp4',
        			movDesc: '它是奥斯卡电影节的负责人，也是导演的作品，讽刺了现代社会的两极分化。',
        			movDescEng: 'It is the head of the Academy Film Festival and is the work of the director, who satirized the polarization of modern society.',
        			movDescJpn: 'アカデミー映画祭ができ薪であり、現代社会の二極化を風刺したポン監督の作品だ。',
        			isMosaic: '',
        			movScore: '',
        			giftTotal: '',
        			showYn: 0,
        			showOrder: '',
        			gmtCreateId: '',
        			gmtCreate: '',
        			modId: '',
        			modDt: '',
        			srchMovName: '',
        			actorList: [ { actorIdx: 2, actorName: '송강호' },
        	            { actorIdx: 3, actorName: '김향기' },
        	           ],
        	        tagListChn: [ 	{id: 1 , movId: 1 , name: '好玩' , langCd: 'CHN' },
        	        				{id: 2 , movId: 1 , name: '利益' , langCd: 'CHN' }
           	         
           	           ],
           	        tagListEng: [ 	{id: 3 , movId: 1 , name: 'fun' , langCd: 'ENG' },
           	        				{id: 4 , movId: 1 , name: 'interest' , langCd: 'ENG' }
	         
           	        ],
           	        
           	        tagListJpn: [ 	{id: 5 , movId: 1 , name: '楽しい' , langCd: 'JPN' },
           	        				{id: 6 , movId: 1 , name: '興味' , langCd: 'JPN' }

           	        ],
           	        
           	     coverChn:  
           	     {coverIdx: 1 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_chn' , horizontalSmall: 'hs' , verticalLarge: 'vl' , verticalSmall: 'vs' , langCd: 'CHN', },
        	     coverEng:  
           	     {coverIdx: 2 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_eng' , horizontalSmall: 'hs' , verticalLarge: 'vl' , verticalSmall: 'vs' , langCd: 'ENG' },
        	     coverJpn:  
           	     {coverIdx: 3 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_jpn' , horizontalSmall: 'hs' , verticalLarge: 'vl' , verticalSmall: 'vs' , langCd: 'JPN' },
        
           	     coverChnFile:  
           	     {coverIdx: 1 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_chn.jpg' , horizontalSmall: 'hs.jpg' , verticalLarge: 'vl.jpg' , verticalSmall: 'vs.jpg' , langCd: 'CHN', },
           	     coverEngFile:  
           	     {coverIdx: 2 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_eng.jpg' , horizontalSmall: 'hs.jpg' , verticalLarge: 'vl.jpg' , verticalSmall: 'vs.jpg' , langCd: 'ENG' },
           	     coverJpnFile:  
           	     {coverIdx: 3 , movId: 1 , coverDomain: 'http://cover..' , horizontalLarge: 'hl_jpn.jpg' , horizontalSmall: 'hs.jpg' , verticalLarge: 'vl.jpg' , verticalSmall: 'vs.jpg' , langCd: 'JPN' },
 
           	     
           	    // coverChnFile[Objerct]: 
        	};

        try {
            // const response = yield axios.get('http://1/api/v1/Movies');
           //  const Movies = response.data;
            
            const newMovie = list;

            this.newMovie = newMovie;
            this.newCoverChn = list.coverChn;
            this.newCoverEng = list.coverEng;
            this.newCoverJpn = list.coverJpn;
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
        const actorList = toJS(this.newMovie.actorList);

        const filteredActorList = _.filter(toJS(this.actorList), (group) => {
            const groupInInviteList = _.find(actorList, (inviteGroup) => inviteGroup.actorIdx === group.actorIdx);
            if(groupInInviteList) {
               return false;
            } else {
                return true;
            }
        });

        return filteredActorList;
    }
    
    @action changeSelectedActor = (actorIdx) => {
    	const selectedGroup = _.find(toJS(this.actorList), (group) => group.actorIdx == actorIdx);
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
	     		const selectTag = {name : tag, langCd : 'CHN'};
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
         		const selectTag = {name : tag, langCd : 'ENG'};
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
         		const selectTag = {name : tag, langCd : 'JPN'};
         		this.newMovie.tagListJpn.push(selectTag);
             }
    	}
    	
    }
    
    @action removeActor = (actorIdx) => {
    	 for(let i=0; i<this.newMovie.actorList.length; i++) {
             if(this.newMovie.actorList[i].actorIdx === actorIdx) {
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
    

    uploadNewMovie = flow(function* uploadNewMovie() {
    	//toJS(this.newMovie.actorList).map((item) => item.actorIdx)
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.name));
    	//alert(toJS(this.newMovie.tagListChn).map((tag) => tag.langCd));
    	// this.movieResult = "pending";
        this.dialogState = DialogState.Adding;

        try {
    		
            this.newMovie.coverChn = this.newCoverChn;
            this.newMovie.coverEng = this.newCoverEng;
            this.newMovie.coverJpn = this.newCoverJpn;
        
            const param = new FormData();
            
          //  param.append('newMovie', this.newMovie);
            param = this.newMovie;
         
            param.append('movieFiles', this.movieFiles);
          //  param.append('filename', filename);
          //  param.append('uploadFile', uploadFile);

            yield axios.post('/api/v1/files/channel-attach', param);
            
            
            // 1. insert moive_bas , get movId          
            // 3. insert movie_cover
            // 4. insert movie_actor_rel 
            // 5. insert movie_tag_txn 
 
            /*            
            // 2. video, image uploadFile test
            const move_id = '1';
            const filepath = move_id;
            const filename ='';
            
            // cover image 파일 중,영,일 4개씩 
                
	        const fileTypeImg = 'image';
	        // 중
	        if( this.newCoverChn.horizontalLarge ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_chn'), this.newCoverChn.horizontalLarge);
           	}
               if( this.newCoverChn.horizontalSmall ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_chn'), this.newCoverChn.horizontalSmall);
           	}
               if( this.newCoverChn.verticalLarge ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_chn'), this.newCoverChn.verticalLarge);
           	}
               
               if( this.newCoverChn.verticalSmall ){
               	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_chn'), this.newCoverChn.verticalSmall);
           	}
           // 영    
           if( this.newCoverEng.horizontalLarge ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_eng'), this.newCoverEng.horizontalLarge);
          	}
              if( this.newCoverEng.horizontalSmall ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_eng'), this.newCoverEng.horizontalSmall);
          	}
              if( this.newCoverEng.verticalLarge ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_eng'), this.newCoverEng.verticalLarge);
          	}
              
              if( this.newCoverEng.verticalSmall ){
              	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_eng'), this.newCoverEng.verticalSmall);
          	}
              //일
          if( this.newCoverJpn.horizontalLarge ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hl_jpn'), this.newCoverJpn.horizontalLarge);
        	}
            if( this.newCoverJpn.horizontalSmall ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_hs_jpn'), this.newCoverJpn.horizontalSmall);
        	}
            if( this.newCoverJpn.verticalLarge ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vl_jpn'), this.newCoverJpn.verticalLarge);
        	}
            
            if( this.newCoverJpn.verticalSmall ){
            	this.uploadFile(fileTypeImg, filename.concat(filepath,'_vs_jpn'), this.newCoverJpn.verticalSmall);
        	}
 
            
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
  */          
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
            this.movieResult = null;
        }
    });
    
    getActorList = flow(function* getActorList() {
    	const list = [{actorIdx: 1, actorName: '정우성' },
            { actorIdx: 2, actorName: '송강호' },
            { actorIdx: 3, actorName: '김향기' },
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