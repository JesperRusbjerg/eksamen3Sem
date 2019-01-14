import {handleHttpErrors, makeOptions} from './FacadeUtils'

const baseURL = "";

class Facade{

getData = async () =>{
    URL = baseURL + ""

    const response = await fetch(URL)
    const data = await handleHttpErrors(response)
    return data
}

postData = async (obj) =>{
    URL = baseURL + ""

    const options = makeOptions("POST", obj)
    const response = await fetch(URL, options)
    const data = await handleHttpErrors(response)
    return data;
}

deleteData = async (id) =>{
    URL = baseURL+"/"+id

    const options = makeOptions("DELETE")
    const response = await fetch(URL, options)
    const data = await handleHttpErrors(response)
    return data;

}

putData = async (obj) =>{
    URL = baseURL + ""

    const options = makeOptions("PUT", obj)
    const response = await fetch(URL, options)
    const data = await handleHttpErrors(response)
    return data;

}



}

export default new Facade();

