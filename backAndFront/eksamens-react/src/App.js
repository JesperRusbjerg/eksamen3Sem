import React, { Component } from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom'

class App extends Component {

  render() {

    return (
      <div>
        <Router>
          <Switch>
            <Route path="/" render={(props) => <Test {...props} />} />
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
  this.setState({msg: "Hejsa M!"})
}

render(){
  return(
    <div>
      <p>{this.state.msg} </p>
      <button onClick={this.clicked}> click me</button>
    </div>
  )
}

}



export default App;
