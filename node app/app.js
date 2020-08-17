const dotenv = require("dotenv");
const request = require("postman-request");
dotenv.config({ path: "./config/config.env" });

const connection = require("./db/mysql_connection");
console.log(count());

let url = `http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/ListPublicReservationSport/1/184/`;

request(url, async (error, response, body) => {
  console.log("error:", error); // Print the error if one occurred
  console.log("statusCode:", response && response.statusCode); // Print the response status code if a response was received
  //   console.log("body:", body); // Print the HTML for the Google homepage.

  let list = await JSON.parse(body).ListPublicReservationSport.row;
  let list_total_count = await JSON.parse(body).ListPublicReservationSport
    .list_total_count;

  if (count() != list_total_count) {
    console.log("시설물 갯수가 달라짐");
    return;
    //   insert(post);
  }
  // 데이터 값
  let jsonObj = list;
  let post = Object.keys(jsonObj).map(function (index) {
    let obj = jsonObj[index];
    return Object.keys(obj).map(function (val) {
      return obj[val];
    });
  });

  //   insert(post);
});

async function count() {
  let query = `select count(SVCID) as count from sport_rows`;
  let count;
  try {
    [reslut] = await connection.query(query);
    count = reslut[0].count;
    console.log(reslut[0].count);
    return await count;
  } catch (e) {
    console.log(e);
  }
}

async function insert(post) {
  let query = `insert into sport_rows(GUBUN,SVCID,MAXCLASSNM,MINCLASSNM,SVCSTATNM,
        SVCNM,PAYATNM,PLACENM,USETGTINFO,SVCURL,X,Y,SVCOPNBGNDT,SVCOPNENDDT,
        RCPTBGNDT,RCPTENDDT,AREANM,IMGURL,DTLCONT,TELNO,V_MIN,V_MAX,
        REVSTDDAYNM,REVSTDDAY) values ?`;
  const [reslut] = await connection.query(query, [post]);
  console.log(reslut);
}
