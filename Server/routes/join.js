var express = require('express');
var router = express.Router();

var db = require('../public/javascripts/room.js')();

router.get('/', function (req, res, next) {
    res.send("test");
});

router.post('/', function (req, res){
    res.send("z");
});

module.exports = router;
