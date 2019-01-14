export function makeOptions(method, body) {
    var opts = {
        method: method,
        headers: {
            "Content-type": "application/json",
            'Accept': 'application/json',
        }
    }
    if (body) {
        opts.body = JSON.stringify(body);
    }
    return opts;
}


export async function handleHttpErrors(res) {
    if (!res.ok) {
        const fullError = await res.json();
        var err = {
            status: res.status,
            fullError
        }
        throw err;
    }
    const json = await res.json();
    return json;
}


