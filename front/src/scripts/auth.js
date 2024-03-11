export const getCredentials = () => {
    if (typeof window !== "undefined") {
        return localStorage.getItem("Auth");
    }
}

export const setCredentials = (username, password) => {
    const credentials = btoa(`${username}:${password}`);
    localStorage.setItem("Auth", credentials);
}

export const removeCredentials = () => {
    localStorage.setItem("Auth", "");
}