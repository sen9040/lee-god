const express = require("express");
const { getSearch } = require("../controllers/search");

const router = express.Router();

router.route("/").get(getSearch);

module.exports = router;
