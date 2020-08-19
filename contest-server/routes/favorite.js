const express = require("express");
const {
  addFavorite,
  getFavorite,
  deleteFavorite,
} = require("../controllers/favorite");

const router = express.Router();

router.route("/").post(addFavorite).get(getFavorite);
router.route("/:id").delete(deleteFavorite);

module.exports = router;
