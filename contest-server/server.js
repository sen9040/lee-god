const express = require("express");
const dotenv = require("dotenv");
dotenv.config({ path: "./config/config.env" });
// 파일 처리를 위한 라이브러리 임포트
const fileupload = require("express-fileupload");
const path = require("path");
// 내가 만든 파일 require는 이 아래에다가 넣자.
const search = require("./routes/search");
const favorite = require("./routes/favorite");
const app = express();
app.use(express.json());
app.use(fileupload());
// 이미지를 불러올 수 있도록 static 경로 설정
app.use(express.static(path.join(__dirname, "public")));

app.use("/api/v1/search", search);
app.use("/api/v1/favorite", favorite);

const PORT = process.env.PORT || 5776;

app.listen(PORT, console.log("서버가동 !" + PORT));
