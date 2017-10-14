const INTERVAL_TIME = 2000;
const servletUrl = buildUrlWithContextPath("GameServlet")

function renderGamesAndUsers(data){
    // $.each(data,(key,value)=>{
    //     console.log(value);
    // })
    const gamesAndUsersObj = JSON.parse(data);
    console.log(gamesAndUsersObj);
}

function userLoggedOut(data) {

}

$(document).ready(
    ()=>{
        setInterval(()=>{
            $.ajax({
                url: `http://localhost:8080/GameServlet`,
                type: "GET",
                data: {},
                success:(data) => renderGamesAndUsers(data) ,
                error: (data) => userLoggedOut(data)
            })
        },INTERVAL_TIME)
    }
)