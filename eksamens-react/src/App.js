import React, { Component } from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom'

class App extends Component {

  
  render() {
    
    return (
      <div>
          <Router>
            <Switch>
               <Route path="/test" render={(props) => <div>TEST3</div>}/>
            </Switch>
          </Router>
      </div>
    );
  }
}


export default App;