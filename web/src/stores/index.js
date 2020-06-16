import { default as AuthStore } from "./AuthStore";
import {default as MovieStore} from "./MovieStore";
import {default as ChannelStore} from "./ChannelStore";
import {default as AdStore} from "./AdStore";
import {default as AdLiveStore} from "./AdLiveStore";
import {default as UserStore} from "./UserStore";
import {default as GiftStore} from "./GiftStore";
import {default as ChannelFileStore} from "./ChannelFileStore";

export const stores = {
    authStore:  new AuthStore(),
    movieStore: new MovieStore(),
    channelStore: new ChannelStore(),
    adStore: new AdStore(),
    adLiveStore: new AdLiveStore(),
    userStore: new UserStore(),
    giftStore: new GiftStore(),
    channelFileStore: new ChannelFileStore(),
};