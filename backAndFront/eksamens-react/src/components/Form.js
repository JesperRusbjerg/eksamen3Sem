import React, { Component } from 'react'
import { Button } from 'react-bootstrap';


// You need to provide a object with with properties and values/empty string,
// and a onSubmit callback(handleSubmit).
// And you can provie a object with types, if non i given all are in text

// const data = {
//     id: 32,
//     name: "Nikolai",
//     age : ""
// }

// const type = {
//     name: "text",
//     age: "number",
//     id: "disabled"
// }

export default class MyForm extends Component {

    constructor(props) {
        super(props);
        const data = JSON.parse(JSON.stringify(props.data))
        this.state = { data, type: props.type }
    }

    componentWillReceiveProps(newProps) {
        const { data, type } = newProps;
        data = JSON.parse(JSON.stringify(data));
        this.setState({ data, type });
    }


    render() {
        const data = this.state.data;
        const typeArr = this.state.type;

        if (!data) return null;

        const jsxArray = [];
        for (var property in data) {
            if (data.hasOwnProperty(property)) {
                let type = "text"
                if (typeArr) {
                    type = typeArr[property];
                }
                const value = data[property];
                const name = property.charAt(0).toUpperCase() + property.slice(1);
                const id = property;
                jsxArray.push(
                    <InputForm
                        handleChange={this.handleChange}
                        key={id}
                        value={value}
                        name={name}
                        id={id}
                        type={type}
                    />)
            }
        }

        return (
            <div>
                <form>
                    {jsxArray}
                    <Button
                        onClick={(event) => this.props.handleSubmit(this.state.data, event)}
                        bsStyle="success">
                        Submit
                    </Button>
                </form>
            </div>
        )
    }

    handleChange = (event) => {
        const data = this.state.data;
        data[event.target.id] = event.target.value;
        this.setState({ data });
    }
}

function InputForm({ value, name, id, type, handleChange }) {
    let input = <input
        disabled={type==="disabled"}
        onChange={handleChange}
        type={type}
        id={id}
        value={value}
        placeholder={name} />

    return (
        <div>
            <label>
                <b> {name}: </b>
                <br />
                {input}
            </label>
        </div>
    );
}