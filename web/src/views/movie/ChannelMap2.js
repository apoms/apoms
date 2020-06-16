import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';

import {
 
    Container
    
} from "@material-ui/core";


const useStyles = makeStyles((theme) => ({
  root: {
    margin: 'auto',
  },
  paper: {
    width: 430,
    height: 640,
    overflow: 'auto',
  },
  button: {
    margin: theme.spacing(0.5, 0),
  },
  
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
	    minWidth: '40%',
	    // display: 'flex',
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
  
  fileboxCover: {
      marginRight: theme.spacing(1),
      marginLeft: theme.spacing(1),
      flexGrow: 98,
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
  
  movieDetailCover: {
	    //marginLeft: theme.spacing(2),
	    padding: 2,
	    
	    //flexGrow: 1,
	    //display: 'flex',
	    minWidth: '83%',
	},
  
  
  CProgress: {
  	// position: 'absolute',
  	// bottom: '10%',
  	left: '50%',
  	// right: '50%',
//, left: 0,
  // right: 0, bottom: 0,
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
    chip: {
      margin: theme.spacing(0.5),
    },
    
}));

function not(a, b) {
  return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a, b) {
  return a.filter((value) => b.indexOf(value) !== -1);
}

export default function TransferList() {
  const classes = useStyles();
  const [checked, setChecked] = React.useState([]);
  const [left, setLeft] = React.useState([0, 1, 2, 3]);
  const [right, setRight] = React.useState([4, 5, 6, 7]);

  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const handleAllRight = () => {
    setRight(right.concat(left));
    setLeft([]);
  };

  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  const handleCheckedLeft = () => {
    setLeft(left.concat(rightChecked));
    setRight(not(right, rightChecked));
    setChecked(not(checked, rightChecked));
  };

  const handleAllLeft = () => {
    setLeft(left.concat(right));
    setRight([]);
  };

  const customList = (items) => (
    <Paper className={classes.paper}>
      <List dense component="div" role="list">
        {items.map((value) => {
          const labelId = `transfer-list-item-${value}-label`;

          return (
            <ListItem key={value} role="listitem" button onClick={handleToggle(value)}>
              <ListItemIcon>
                <Checkbox
                  checked={checked.indexOf(value) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{ 'aria-labelledby': labelId }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={`List item ${value + 1}`} />
            </ListItem>
          );
        })}
        <ListItem />
      </List>
    </Paper>
  );

  return (
		  
		  <Container component="main" className={classes.mainContainer}>
          
          <div className={classes.appBarSpacer} />
        
          <div className={classes.mainContent}>
          
          
		    <Grid container spacing={2} justify="center" alignItems="center" className={classes.root}>
		      <Grid item>{customList(left)}</Grid>
		      <Grid item>
		        <Grid container direction="column" alignItems="center">
		          <Button
		            variant="outlined"
		            size="small"
		            className={classes.button}
		            onClick={handleAllRight}
		            disabled={left.length === 0}
		            aria-label="move all right"
		          >
		            ≫
		          </Button>
		          <Button
		            variant="outlined"
		            size="small"
		            className={classes.button}
		            onClick={handleCheckedRight}
		            disabled={leftChecked.length === 0}
		            aria-label="move selected right"
		          >
		            &gt;
		          </Button>
		          <Button
		            variant="outlined"
		            size="small"
		            className={classes.button}
		            onClick={handleCheckedLeft}
		            disabled={rightChecked.length === 0}
		            aria-label="move selected left"
		          >
		            &lt;
		          </Button>
		          <Button
		            variant="outlined"
		            size="small"
		            className={classes.button}
		            onClick={handleAllLeft}
		            disabled={right.length === 0}
		            aria-label="move all left"
		          >
		            ≪
		          </Button>
		        </Grid>
		      </Grid>
		      <Grid item>{customList(right)}</Grid>
		    </Grid>
    
    </div>
    </Container>
    
  );
}
