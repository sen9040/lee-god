const dotenv = require("dotenv");
const request = require("postman-request");
const proj4 = require("proj4");
dotenv.config({ path: "./config/config.env" });

var firstProjection =
  "+proj=tmerc +lat_0=38 +lon_0=128 +k=0.9999 +x_0=288250 +y_0=500100 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43";
var secondProjection = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";
// #1. 변환한 위도 경도 값 저장
var lonAndLat1 = proj4(firstProjection, secondProjection, [
  211380.5791,
  446815.9156,
]);

console.log(lonAndLat1);
