import {action, computed, flow, observable} from "mobx";
import axios from "axios";
import fileDownload from "js-file-download";
import _ from "lodash";

const FileDialogState = {
    Closed: 'Closed',
    Loading: 'Loading',
    Loaded: 'Loaded',
    LoadFailed: 'LoadFailed',
    Uploading: 'Uploading',
    Uploaded: 'Uploaded',
    UploadFailed: 'UploadFailed',
}


export default class ChannelFileStore {
    @observable channelId = '';
    @observable channelUserId = '';
    @observable fileDialogState = FileDialogState.Closed;
    @observable paging = {
        totalCount: 0,
        page: 0,
        rowsPerPage: 10,
    }
    @observable fileList = [];
    @observable upload = {
        comment: '',
        file: '',
    }

    @action clearFileDialogState = (open) => {
        if(open) {
            this.fileDialogState = FileDialogState.Loaded;
        } else {
            this.fileDialogState = FileDialogState.Closed;
        }
    }

    @action changeUploadComment = (comment) => {
        this.upload.comment = comment;
    }

    @action changeUploadFile = (file) => {
        this.upload.file = file;
    }

    @action changePagingPage = (page) => {
        this.paging.page = page;
    }

    @action changePagingRowsPerPage = (rowsPerPage) => {
        this.paging.rowsPerPage = rowsPerPage;
    }

    @computed get isOpenFileDialog() {
        return this.fileDialogState !== FileDialogState.Closed;
    }

    @computed get isLoadFailed() {
        return this.fileDialogState === FileDialogState.LoadFailed;
    }

    @computed get isUploading() {
        return this.fileDialogState === FileDialogState.Uploading;
    }

    @computed get isUploadFailed() {
        return this.fileDialogState === FileDialogState.UploadFailed;
    }

    @computed get isUploadable() {
        return this.upload.file !== undefined && this.upload.file !== null && this.upload.file !== '';
    }

    openFileDialog = flow(function* openFileDialog(channelId, channelUserId) {
        this.channelId = channelId;
        this.channelUserId = channelUserId;
        this.fileList = [];
        this.fileDialogState = FileDialogState.Loading;

        try {
            const response = yield axios.get(`/api/v1/channels/files?channel-id=${channelId}`);
            const fileList = _.sortBy(response.data, ['fileId']);


            this.fileList = fileList;
            this.paging.totalCount = fileList.length;
            this.fileDialogState = FileDialogState.Loaded;
        } catch(error) {
            this.fileList = [];
            this.paging.totalCount = 0;
            this.fileDialogState = FileDialogState.LoadFailed;
        }
    })

    uploadFile = flow(function* uploadFile(userId) {
        this.fileDialogState = FileDialogState.Uploading;

        try {
            const param = new FormData();
            param.append('channelAttach', JSON.stringify({
                channelId: this.channelId,
                userId: userId,
                comment: this.upload.comment,
                sortOrder: 1,
            }));
            param.append('file', this.upload.file);

            yield axios.post('/api/v1/files/channel-attach', param);

            this.upload.comment = '';
            this.upload.file = '';

            const response = yield axios.get(`/api/v1/channels/files?channel-id=${this.channelId}`);
            const fileList = _.sortBy(response.data, ['fileId']);

            this.fileList = fileList;
            this.paging.totalCount = fileList.length;
            this.fileDialogState = FileDialogState.Uploaded;
        } catch(error) {
            this.fileDialogState = FileDialogState.UploadFailed;
        }
    })

    downloadFile = flow(function* downloadFile(fileId, fileName) {
        const fileInfo = _.find(this.fileList, (file) => file.fileId === fileId);

        if(fileInfo) {
            fileInfo.downloading = true;

            try {
                const response = yield axios.get(`/api/v1/files/channel-attach?file-id=${fileId}`, {responseType: 'blob'});
                const file = response.data;

                fileDownload(file, fileName);
                fileInfo.downloading = false;
            } catch (error) {

            }
        }
    })
}