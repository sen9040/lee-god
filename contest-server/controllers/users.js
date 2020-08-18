const connection = require("../db/mysql_connection");
const validator = require("validator");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

// @desc        회원가입
// @route       POST /api/v1/users
// @request     email, passwd
// @response    success
exports.createUser = async (req, res, next) => {
  let email = req.body.email;
  let passwd = req.body.passwd;

  if (!email || !passwd) {
    res.status(400).json({ message: "파라미터가 잘못 되었습니다." });
    return;
  }
  if (!validator.isEmail(email)) {
    res.status(400).json();
    return;
  }

  const hashedPasswd = await bcrypt.hash(passwd, 8);

  let query = `insert into photo_user (email,passwd) values("${email}","${hashedPasswd}")`;

  let user_id;
  try {
    [result] = await connection.query(query);
    user_id = result.insertId;
  } catch (e) {
    res.status(500).json({ success: false, e });
    return;
  }

  const token = jwt.sign({ user_id: user_id }, process.env.ACCESS_TOKEN_SECRET);

  query = `insert into photo_user_token (user_id,token) values (${user_id},"${token}")`;

  try {
    [result] = await connection.query(query);
  } catch (e) {
    res.status(500).json({ success: false, e });
  }

  res.status(200).json({ success: true, token: token });
};

// @desc     로그인
// @route    POST /api/v1/users/login
// @request  email, passwd
// @response success, token
exports.loginUser = async (req, res, next) => {
  let email = req.body.email;
  let passwd = req.body.passwd;

  if (!email || !passwd) {
    res.status(400).json({ message: "파라미터가 잘못 되었습니다." });
    return;
  }
  let query = "select * from photo_user where email = ? ";
  let data = [email];

  let user_id;

  try {
    [rows] = await connection.query(query, data);
    let hashedPasswd = rows[0].passwd;
    user_id = rows[0].id;
    const isMatch = await bcrypt.compare(passwd, hashedPasswd);
    if (isMatch == false) {
      res.status(401).json();
      return;
    }
  } catch (e) {
    res.status(500).json();
    return;
  }
  const token = jwt.sign({ user_id: user_id }, process.env.ACCESS_TOKEN_SECRET);
  query = "insert into photo_user_token (user_id, token) values (?,?)";
  data = [user_id, token];
  try {
    [result] = await connection.query(query, data);
    res.status(200).json({ success: true, token: token });
  } catch (e) {
    res.status(500).json();
  }
};

// @desc        로그아웃 (기기1대 로그아웃)
// @route       delete /api/v1/users/logout
// @request     token(auth), user_id(auth)
// @response    success

exports.logout = async (req, res, next) => {
  let user_id = req.user.id;
  let token = req.user.token;

  console.log(user_id + "," + token);

  let query = "delete from photo_user_token where user_id = ? and token = ?";
  let data = [user_id, token];

  try {
    [result] = await connection.query(query, data);
    res.status(200).json({ success: true });
  } catch (e) {
    res.status(500).json();
  }
};
