const connection = require("../db/mysql_connection");
const validator = require("validator");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

// @desc        검색
// @route       GET /api/v1/search?keyword
// @request
// @response    success, cnt, items[]
exports.getSearch = async (req, res, next) => {
  let keyword = req.query.keyword;

  query = `select * from sport_rows where SVCNM like  "%${keyword}%"`;

  try {
    [rows] = await connection.query(query);
    res.status(200).json({ success: true, cnt: rows.length, items: rows });
  } catch (e) {
    res.status(500).json({ success: false, e });
  }
};
