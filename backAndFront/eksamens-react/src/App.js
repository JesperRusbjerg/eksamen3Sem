import React, { Component } from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom'
import HomePage from './routes/HomePage'

class App extends Component {

  render() {
    return (
      <div>
        <Router>
          <Switch>
            <Route path="/" render={(props) => <HomePage {...props} />} />
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;
