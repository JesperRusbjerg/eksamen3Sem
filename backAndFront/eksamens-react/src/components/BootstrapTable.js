import React from 'react'
import loadGif from './../resources/giphy.gif'

import BootstrapTable from 'react-bootstrap-table-next';
import filterFactory, { textFilter, numberFilter } from 'react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';

const options = {
    sizePerPageList: 
    [{
      text: "four", value: 4
    },{
      text: "five", value: 5
    }
    ]
  };

  //Takes an array as argument
  function generateColumns(c){
    const toFilter = ["Name"]
    const toFilterNumber = [""]

    const columns = c.map((c) => {
        const newCol = {
        dataField: c,
        text: c,
        sort: true
        }

      if(toFilter.includes(c)){
        newCol["filter"] = textFilter()
      }
      if(toFilterNumber.includes(c)){
        newCol["filter"] = numberFilter()
      }
      return newCol;
    });
    return columns;
  }

  //Takes an array as argument
  //ToChange
  function dataConverter(data){
    const newData = data.map((data) => {
      return {
        //When making data, if colum includes space orso, make String and insert instead
        Name: data.name,
        Age: data.age,
        CPR: data.cpr,
        Phones: multiArrayTableBootStrap(data.phones),
      }
    });
  return newData;
  }
  

export default function BsTable({labels, data}){

    if(data == null){
        return(
            <img src={loadGif} alt="loading..." />
        )
      }

      if(labels == null){
        labels = Object.getOwnPropertyNames(data[0]);
        labels = labels.map((e) => e.charAt(0).toUpperCase() + e.slice(1));
      }

      return(
      <BootstrapTable
      striped
      hover
      keyField='CPR'
      data={dataConverter(data)}
      columns={generateColumns(labels)}
      pagination={ paginationFactory(options) }
      filter={ filterFactory()}

    />
      )
    
}

//Takes array and displays array[0] + length of array-1 in a (+3) fashion for example
function multiArrayTableBootStrap(array){
    if(array.length === 1){
      return array[0]
    }
    else{
      var number = array.length-1
      return array[0] + `(+${number})`
    }
  }