const dotenv = require("dotenv");
const request = require("postman-request");
dotenv.config({ path: "./config/config.env" });

const connection = require("./db/mysql_connection");

// let url = `http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/ListPublicReservationSport/1/184/`;
// let park_url = `http://openapi.seoul.go.kr:8088/474f4e6f42746b6436386354566d65/json/SearchParkInfoService/1/132`;
let way_url = `http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/SeoulGilWalkCourse/1001/1455/`;

request(way_url, async (error, response, body) => {
  console.log("error:", error); // Print the error if one occurred
  console.log("statusCode:", response && response.statusCode); // Print the response status code if a response was received
  //   console.log("body:", body); // Print the HTML for the Google homepage.

  // let list = await JSON.parse(body).ListPublicReservationSport.row;
  // let list_total_count = await JSON.parse(body).ListPublicReservationSport
  //   .list_total_count;

  // let list = await JSON.parse(body).SearchParkInfoService.row;
  // let list_total_count = await JSON.parse(body).SearchParkInfoService
  //   .list_total_count;

  let list = await JSON.parse(body).SeoulGilWalkCourse.row;
  let list_total_count = await JSON.parse(body).SeoulGilWalkCourse
    .list_total_count;

  // 데이터 값
  let jsonObj = list;
  let post = Object.keys(jsonObj).map(function (index) {
    let obj = jsonObj[index];
    return Object.keys(obj).map(function (val) {
      return obj[val];
    });
  });

  way_insert(post);
});

// let park_url = `http://openapi.seoul.go.kr:8088/474f4e6f42746b6436386354566d65/json/SearchParkInfoService/1/132`;

// request(park_url, async (error, response, body) => {
//   console.log("error:", error); // Print the error if one occurred
//   console.log("statusCode:", response && response.statusCode); // Print the response status code if a response was received
//   //   console.log("body:", body); // Print the HTML for the Google homepage.

//   let list = await JSON.parse(body).SearchParkInfoService.row;
//   let list_total_count = await JSON.parse(body).SearchParkInfoService
//     .list_total_count;

//   // 데이터 값
//   let jsonObj = list;
//   let post = Object.keys(jsonObj).map(function (index) {
//     let obj = jsonObj[index];
//     return Object.keys(obj).map(function (val) {
//       return obj[val];
//     });
//   });

//   park_insert(post);
// });

// async function count() {
//   let query = `select count(P_IDX) as count from park_rows`;
//   let count;
//   try {
//     [reslut] = await connection.query(query);
//     count = reslut[0].count;
//     console.log(reslut[0].count);
//     return await count;
//   } catch (e) {
//     console.log(e);
//   }
// }

// async function insert(post) {
//   let query = `insert into sport_rows(GUBUN,SVCID,MAXCLASSNM,MINCLASSNM,SVCSTATNM,
//         SVCNM,PAYATNM,PLACENM,USETGTINFO,SVCURL,X,Y,SVCOPNBGNDT,SVCOPNENDDT,
//         RCPTBGNDT,RCPTENDDT,AREANM,IMGURL,DTLCONT,TELNO,V_MIN,V_MAX,
//         REVSTDDAYNM,REVSTDDAY) values ?`;
//   const [reslut] = await connection.query(query, [post]);
//   console.log(reslut);
// }

// async function park_insert(post) {
//   let query = `insert into park_rows(P_IDX, P_PARK,P_LIST_CONTENT,AREA,OPEN_DT,MAIN_EQUIP,MAIN_PLANTS,GUIDANCE,VISIT_ROAD,USE_REFER,P_IMG,P_ZONE,P_ADDR,P_NAME,P_ADMINTEL,G_LONGITUDE,G_LATITUDE,LONGITUDE,LATITUDE,TEMPLATE_URL) values ?`;
//   const [reslut] = await connection.query(query, [post]);
//   console.log(reslut);
// }

async function way_insert(post) {
  let query = `insert into way_rows(COURSE_CATEGORY,
    COURSE_CATEGORY_NM,
    SOUTH_NORTH_DIV,
    SOUTH_NORTH_DIV_NM,
    AREA_GU,
    DISTANCE,
    LEAD_TIME,
    COURSE_LEVEL,
    VOTE_CNT,
    RELATE_SUBWAY,
    TRAFFIC_INFO,
    CONTENT,
    PDF_FILE_PATH,
    COURSE_NAME,
    REG_DATE,
    DETAIL_COURSE,
    CPI_IDX,
    CPI_NAME,	
    X,
    Y,
    CPI_CONTENT) values ?`;
  const [reslut] = await connection.query(query, [post]);
  console.log(reslut);
}
