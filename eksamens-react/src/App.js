import React, { Component } from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom'

class App extends Component {

  render() {

    return (
      <div>
        <Router>
          <Switch>
            <Route path="/" render={(props) => <Test {...props}/>} />
          </Switch>
        </Router>
      </div>
    );
  }
}

class Test extends Component{
  constructor(props){
    super(props)
    this.state = {msg: "hejsa P"}
  }


clicked = () =>{
  this.setState({msg: "ludeerrrr"})
}

render(){
  return(
    <div>
      <Test2 hej={this.state.msg}/>
      <button onClick={this.clicked}> click me</button>
    </div>
  )
}

}



export default App;
