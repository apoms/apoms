import React from 'react';
import PropTypes from 'prop-types';

import TreeView from '@material-ui/lab/TreeView';
import TreeItem from '@material-ui/lab/TreeItem';
import Typography from '@material-ui/core/Typography';
import MailIcon from '@material-ui/icons/Mail';
import DeleteIcon from '@material-ui/icons/Delete';
import Label from '@material-ui/icons/Label';
import SupervisorAccountIcon from '@material-ui/icons/SupervisorAccount';
import InfoIcon from '@material-ui/icons/Info';
import ForumIcon from '@material-ui/icons/Forum';
import LocalOfferIcon from '@material-ui/icons/LocalOffer';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import ArrowRightIcon from '@material-ui/icons/ArrowRight';

import {
    Divider,
    Drawer,
    Hidden,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    ListSubheader,
    Toolbar
} from "@material-ui/core";

import {Link} from "react-router-dom";

import {makeStyles, useTheme} from "@material-ui/core/styles";

import ComputerIcon from '@material-ui/icons/Computer';
import CallMissedOutgoingIcon from '@material-ui/icons/CallMissedOutgoing';
import DnsIcon from "@material-ui/icons/Dns";
import VpnKeyIcon from "@material-ui/icons/VpnKey";

import VideoLibrary from '@material-ui/icons/VideoLibrary';
import LiveTvIcon from '@material-ui/icons/LiveTv';
import PaymentIcon from '@material-ui/icons/Payment';


const logoWidth = 129;
const logoHeight = 22;

const useStyles = makeStyles((theme) => ({
    drawer: {
        [theme.breakpoints.up('sm')]: {
            width: theme.drawerWidth,
            flexShrink: 0,
        },
    },
    appBar: {
        width: theme.drawerWidth,
        paddingLeft: 0,
        paddingRight: 0,
        ...theme.mixins.toolbar,
    },
    drawerPaper: {
        width: theme.drawerWidth,
        height: '100%',
        border: 'none',
    },
    toolbar: {
        width: theme.drawerWidth,
        backgroundColor: theme.palette.primary.main,
        paddingLeft: 0,
        paddingRight: 0,
        boxShadow: '0px 2px 4px -1px rgba(0,0,0,0.2),0px 4px 5px 0px rgba(0,0,0,0.14),0px 1px 10px 0px rgba(0,0,0,0.12)',
    },
    logo: {
        width: logoWidth,
        height: logoHeight,
        marginLeft: (theme.drawerWidth - logoWidth) / 2,
        marginRight: (theme.drawerWidth - logoWidth) / 2,
    },
    menu: {
        borderRight: '1px solid rgba(0,0,0,0.12)',
        height: '100%',
    },

    link: {
        textDecoration: 'none',
        color: 'inherit',
    },
    
    root: {
        color: theme.palette.text.secondary,
        '&:hover > $content': {
          backgroundColor: theme.palette.action.hover,
        },
        '&:focus > $content, &$selected > $content': {
          backgroundColor: `var(--tree-view-bg-color, ${theme.palette.grey[400]})`,
          color: 'var(--tree-view-color)',
        },
        '&:focus > $content $label, &:hover > $content $label, &$selected > $content $label': {
          backgroundColor: 'transparent',
        },
       /* height: 264,*/
	    flexGrow: 1,
	    maxWidth: 400,
	    marginTop: '10px',
	   
      },
      content: {
        color: theme.palette.text.secondary,
        borderTopRightRadius: theme.spacing(2),
        borderBottomRightRadius: theme.spacing(2),
        paddingRight: theme.spacing(1),
        fontWeight: theme.typography.fontWeightMedium,
        '$expanded > &': {
          fontWeight: theme.typography.fontWeightRegular,
        },
      },
      group: {
        marginLeft: 0,
        '& $content': {
          paddingLeft: theme.spacing(2),
        },
      },
      expanded: {},
      selected: {},
      label: {
        fontWeight: 'inherit',
        color: 'inherit',
      },
      labelRoot: {
        display: 'flex',
        alignItems: 'center',
        padding: theme.spacing(0.5, 0),
        /*color: 'red',*/
        fontSize:'1.875rem',
      },
      labelIcon: {
        marginRight: theme.spacing(1),
      },
      labelText: {
        fontWeight: 'inherit',
        flexGrow: 1,
        fontSize:'1.0rem',
      },
      
}));

function StyledTreeItem(props) {
	  const classes = useStyles();
	  const { labelText, labelIcon: LabelIcon, labelInfo, color, bgColor, ...other } = props;

	  return (
	    <TreeItem
	      label={
	        <div className={classes.labelRoot}>
	          <LabelIcon color="inherit" className={classes.labelIcon} />
	          <Typography variant="body2" className={classes.labelText}>
	            {labelText}
	          </Typography>
	          <Typography variant="caption" color="inherit">
	            {labelInfo}
	          </Typography>
	        </div>
	      }
	      style={{
	        '--tree-view-color': color,
	        '--tree-view-bg-color': bgColor,
	      }}
	      classes={{
	        root: classes.root,
	        content: classes.content,
	        expanded: classes.expanded,
	        selected: classes.selected,
	        group: classes.group,
	        label: classes.label,
	      }}
	      {...other}
	    />
	  );
	}

	StyledTreeItem.propTypes = {
	  bgColor: PropTypes.string,
	  color: PropTypes.string,
	  labelIcon: PropTypes.elementType.isRequired,
	  labelInfo: PropTypes.string,
	  labelText: PropTypes.string.isRequired,
	};


export default function SideMenu(props) {
    const classes = useStyles();
    const theme = useTheme();
    const { mobileOpen, setMobileOpen, isLoggedIn } = props;

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    };

    const drawer = (
        <div className={classes.menu}>
	        <TreeView
	        className={classes.root}
	        defaultExpanded={['']}
	        defaultCollapseIcon={<ArrowDropDownIcon />}
	        defaultExpandIcon={<ArrowRightIcon />}
	        defaultEndIcon={<div style={{ width: 24 }} />}
	      >
	        <ListSubheader inset>관리메뉴리스트</ListSubheader>

	        <StyledTreeItem nodeId="1" labelText="동영상 관리" labelIcon={VideoLibrary}>
	        
	        	<Link to='/movie/MovieList' className={classes.link}>	       
			        <StyledTreeItem
			        nodeId="11"
			        labelText="동영상등록"
			        labelIcon={SupervisorAccountIcon}
			        labelInfo="90"
			        color="#1a73e8"
			        bgColor="#e8f0fe"
			      />
			      <StyledTreeItem
			        nodeId="12"
			        labelText="채널매핑"
			        labelIcon={InfoIcon}
			        labelInfo="2,294"
			        color="#e3742f"
			        bgColor="#fcefe3"
			      />
			      <StyledTreeItem
			        nodeId="13"
			        labelText="채널관리"
			        labelIcon={ForumIcon}
			        labelInfo="3,566"
			        color="#a250f5"
			        bgColor="#f3e8fd"
			      />	
			    </Link>
			</StyledTreeItem>
<!--		        
	        <StyledTreeItem nodeId="2" labelText="생방송 관리" labelIcon={LiveTvIcon}>
		        <StyledTreeItem
	            nodeId="21"
	            labelText="생방송현황"
	            labelIcon={SupervisorAccountIcon}
	            labelInfo="90"
	            color="#1a73e8"
	            bgColor="#e8f0fe"
	          />
	          <StyledTreeItem
	            nodeId="22"
	            labelText="생방송제어"
	            labelIcon={InfoIcon}
	            labelInfo="2,294"
	            color="#e3742f"
	            bgColor="#fcefe3"
	          />
          
	        </StyledTreeItem>
	        
		   
	        <StyledTreeItem nodeId="3" labelText="광고 관리" labelIcon={LocalOfferIcon}>
	        <Link to='/ad/AdList' className={classes.link}>  
	        <StyledTreeItem
	            nodeId="31"
	            labelText="생방송광고"
	            labelIcon={SupervisorAccountIcon}
	            labelInfo="90"
	            color="#1a73e8"
	            bgColor="#e8f0fe"
	          />
	          <StyledTreeItem
	            nodeId="32"
	            labelText="동영상광고"
	            labelIcon={InfoIcon}
	            labelInfo="2,294"
	            color="#e3742f"
	            bgColor="#fcefe3"
	          />
	          <StyledTreeItem
	            nodeId="33"
	            labelText="광고분류"
	            labelIcon={ForumIcon}
	            labelInfo="3,566"
	            color="#a250f5"
	            bgColor="#f3e8fd"
	          />
	            	 </Link>	 
	        </StyledTreeItem>
	         
	        <StyledTreeItem nodeId="4" labelText="기준 정보관리" labelIcon={Label}>
	          <StyledTreeItem
	            nodeId="41"
	            labelText="이용자관리"
	            labelIcon={SupervisorAccountIcon}
	            labelInfo="90"
	            color="#1a73e8"
	            bgColor="#e8f0fe"
	          />
	          <StyledTreeItem
	            nodeId="42"
	            labelText="배우관리"
	            labelIcon={InfoIcon}
	            labelInfo="2,294"
	            color="#e3742f"
	            bgColor="#fcefe3"
	          />
	          <StyledTreeItem
	            nodeId="43"
	            labelText="포인트선물"
	            labelIcon={ForumIcon}
	            labelInfo="3,566"
	            color="#a250f5"
	            bgColor="#f3e8fd"
	          />
	          <StyledTreeItem
	            nodeId="44"
	            labelText="호스트관리"
	            labelIcon={LocalOfferIcon}
	            labelInfo="733"
	            color="#3c8039"
	            bgColor="#e6f4ea"
	          />
	        </StyledTreeItem>
	        <StyledTreeItem nodeId="5" labelText="결제 정산관리" labelIcon={PaymentIcon} />
	        <StyledTreeItem nodeId="6" labelText="History" labelIcon={Label} />
	      </TreeView>

        <Divider />
        </div>
    );

    return (
        <nav className={classes.drawer} aria-label="mailbox folders">
            <Hidden smUp implementation="css">

                <Drawer variant="temporary"
                        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
                        open={mobileOpen}
                        onClose={handleDrawerToggle}
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                        ModalProps={{
                            keepMounted: true, // Better open performance on mobile.
                        }}
                >
                    <Toolbar className={classes.toolbar}>
                        <Link to='/' className={classes.link}>
                            <img src="/images/aether_white.png" alt="AetherIT" className={classes.logo}/>
                        </Link>
                    </Toolbar>
                    {isLoggedIn ? (
                        drawer
                    ) : (
                    		''
                    )}
                </Drawer>
            </Hidden>
            <Hidden xsDown implementation="css">
                <Drawer variant="permanent"
                        classes={{
                            paper: classes.drawerPaper,
                        }}

                        open
                >
                    <Toolbar className={classes.toolbar}>
                        <Link to='/' className={classes.link}>
                            <img src="/images/aether_white.png" alt="AetherIT" className={classes.logo}/>
                        </Link>
                    </Toolbar>
                    {isLoggedIn ? (
                        drawer
                    ) : (
                    		''
                    )}
                </Drawer>
            </Hidden>
        </nav>
    );
};