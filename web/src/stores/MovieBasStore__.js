/**
 * 
 */

import {action, flow, observable} from "mobx";
import axios from "axios";

	const EmptyMovieBas  = {
			mov_Id: 0,
			mov_name: '',
			mins: '',
			play_cnt: '',
			play_cnt_str: '',
			down_cnt: '',
			has_down: '',			
			love_cnt: '',
			has_love: '',
			up_cnt: '',
			has_up: '',			
			diss_cnt: '',
			has_diss: '',
			mov_sn: '',
			mov_sn_ori: '',			
			mov_type: '',
			mov_provider: '',
			domain: '',
			f240P: '',			
			f360P: '',
			f480P: '',
			f720P: '',
			f1080P: '',
			mov_desc: '',
			is_mosaic: '',
			mov_score: '',
			gift_total: '',
			show_yn: '',
			show_order: '',
			gmt_create_Id: '',
			gmt_create: '',
			mod_id: '',
			mod_dt: '',
		};
		
	const ListState = {
        Loading: 'Loading',
        Loaded: 'Loaded',
        LoadFailed: 'LoadFailed',
    };

    const initState = {

        iamPortData : {
                userCode: 'imp20316020',						// 상점 MID
                pg: 'html5_inicis',                           	// PG사
                pay_method: '',                           		// 결제수단  
                merchant_uid: '',  								// 주문번호 (by lee ==> item_id)
                name: '',                                   	// 주문명
                amount:0,                                   	// 결제금액
                buyer_name: '',                             	// 구매자 이름
                buyer_tel: '',                              	// 구매자 전화번호
                buyer_email: '',                            	// 구매자 이메일
                digital:true,									// 휴대폰결제때 true
                response:'',            
        },

        paymentData : {
            paymentId: '',
            itemId: '',
            userId: '',
            amount: '',            
            statusCode: '',
            paymentMethodCode: '',
            paymentMethodName: '',
            bankName: '',
            bankAccountName: '',
            bankAccountNo: '',
            createdDatetime: '',
            createdIp: '',
        },

        paymentTransferData : {
            paymentId: '',
            itemId: '',
            userId: '',
            amount: '',            
            statusCode: '',
            paymentMethodCode: '',           
            bankName: '기업은행',
            bankAccountName: '(주)아이테르정보기술',
            bankAccountNo: '1111-2222-3333-4444',
            paymentState: '',
            createdDatetime: '',
            createdIp: '',
        },
        paymentResult: null,
    };


export default class MovieBasStore {
    @observable iamPortData = initState.iamPortData;
    @observable paymentData = initState.paymentData;
    @observable paymentTransferData = initState.paymentTransferData;
    @observable paymentResult = initState.paymentResult;

    @action init = () => {
      //  this.iamPortData = initState.iamPortData;
      //  this.paymentData = initState.paymentData;
      //  this.paymentTransferData = initState.paymentTransferData;
      //  this.paymentResult = initState.paymentResult;
    };

    @action changeIamPortData = (target, value) => {
       this.iamPortData[target] = value;
    };

    @action changePaymentData = (target, value) => {
       this.paymentData[target] = value;
    };

    @action changePaymentTransferData = (target, value) => {
       this.paymentTransferData[target] = value;
    };

    @action changePaymentResult = (value) => {
        this.paymentResult = value;
    };

    @observable paymentMethodList = [];

    setPaymentInfo = flow(function* setPaymentInfo() {

        this.paymentResult = "pending";
        try {
            const data = {
                paymentId:this.paymentData.paymentId, // UUID.randomUUID().toString()
                itemId:this.paymentData.itemId,
                userId:this.paymentData.userId,    // simpleUser.getUserId()
                amount:this.iamPortData.amount,                
                statusCode: 'COMPLETED',
                paymentMethodCode:this.paymentData.paymentMethodCode,
                bankName:this.paymentData.bankName,
                bankAccountName:this.paymentData.bankAccountName,
                bankAccountNo:this.paymentData.bankAccountNo,
                createdDatetime: this.paymentData.createdDatetime,
                createdIp:this.paymentData.createdIp,
            };
  
            console.log(data.amount);
   
            const response = yield axios.post('/api/v1/payments', data);
 
            if(response.status !== 200) {
                new Error("payment fail");
            }
            this.paymentResult = true;
            // response
        } catch (e) {
            console.log('error');
            console.log(e);
           
            this.paymentResult = false;
        }
    });
  
    getPaymentMethod = flow(function* getPaymentMethod() {

        this.listState = ListState.Loading;
        
        try {
            const response = yield axios.get(`/api/v1/payments/methods`);
            this.paymentMethodList = response.data;
            this.listState = ListState.Loaded;
        } catch (e) {
            this.listState = ListState.LoadFailed;
        }
    });

}