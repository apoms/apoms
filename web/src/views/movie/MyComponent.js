import React, { Component } from 'react';

class MyComponent extends Component
{    
    state = {
        games : ["리그 오브 레전드", "배틀 그라운드", "오버워치"],
        game : ""												  
    }

    textChange = (e) => {	// input 요소의 onChange 이벤트 핸들러
        this.setState({
            game : e.target.value
        });
    }

    add = () =>{			// Button 요소의 onClick 이벤트 핸들러
        this.setState({
            games : this.state.games.concat(this.state.game)
        });
    }

    render(){
        const gameList = this.state.games.map(
            (game, index) => (<li key = {index}>{game}</li>)
        );

        return(
            <div>
                <input type = "text" onChange = {this.textChange}></input>
                <button onClick = {this.add}>추가</button>
                <ul>
                    {gameList}
                </ul>
            </div>
        );
    };
}

export default MyComponent;