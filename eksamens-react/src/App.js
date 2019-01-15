import React, { Component } from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom'

class App extends Component {

  render() {

    return (
      <div>
        <Router>
          <Switch>
            <Route path="/" render={(props) => <div>TEST</div>} />
          </Switch>
        </Router>
      </div>
    );
  }
}



export default App;
