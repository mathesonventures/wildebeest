
		var initDur = 1000;

		$(document).ready(function() {
			// Start it up -  the clouds!!
/*
			setTimeout("generateCloud(0);", (Math.random() * 10000));
			setTimeout("generateCloud(1);", (Math.random() * 20000));
			setTimeout("generateCloud(2);", (Math.random() * 30000));
*/

			setTimeout("startAnim()", 1000);
		});

		function startAnim() {

			$("#sun").animate({
					path : new $.path.arc({
						center:[420, 140],
						radius: 250,
						start: -70,
						end: -190,
						dir: -1
					})
				},
				initDur);

			generateCloud(0, true, true);
			generateCloud(0, true, false);
			generateCloud(1, true, true);
			generateCloud(1, true, false);
			generateCloud(2, true, true);
			generateCloud(2, true, false);

			generateHerds();

		}

		var clouds = new Array();

		clouds[0] = { filename: "assets/hd_cloud_l_w_01.png", width: 81, height: 54, plane: 0 };
		clouds[1] = { filename: "assets/hd_cloud_l_w_02.png", width: 103, height: 68, plane: 0 };
		clouds[2] = { filename: "assets/hd_cloud_l_w_03.png", width: 108, height: 49, plane: 0 };
		clouds[3] = { filename: "assets/hd_cloud_l_w_04.png", width: 123, height: 76, plane: 0 };
		clouds[4] = { filename: "assets/hd_cloud_l_w_05.png", width: 96, height: 38, plane: 0 };
		clouds[5] = { filename: "assets/hd_cloud_l_w_06.png", width: 86, height: 44, plane: 0 };

		var planes = new Array();
		planes[0] = { dur: 120000, maxTop: 70, alpha: 0.85, z: 20 };
		planes[1] = { dur: 160000, maxTop: 60, alpha: 0.7, z: 10 };
		planes[2] = { dur: 240000, maxTop: 40, alpha: 0.6, z: 10 };

		var nextCloudId = 0;

		function generateCloud(
			planeIndex,
			isInit,
			repeat) {

			var cloudIndex = Math.floor(Math.random() * clouds.length);
			var cloud = clouds[cloudIndex];
			var cloudId = nextCloudId++;

			var plane = planes[planeIndex];

			var top = Math.floor(Math.random() * plane.maxTop);

			var cloudHtml = htmlEl(
					"img",
					htmlAtt("id", "cloud" + cloudId) +
					htmlAtt("src", cloud.filename) +
					htmlAtt("width", cloud.width) +
					htmlAtt("height", cloud.height) +
					htmlAtt(
						"style",
						cssProp("position", "absolute") +
						cssProp("top", top + "px") +
						cssProp("left", "-" + (cloud.width + 5) + "px") +
						cssProp("opacity", randomVariation(plane.alpha, 0.1)) +
						cssProp("z-index", plane.z))
				);

			$("#clouds").prepend(cloudHtml);

			var dur = randomVariation(plane.dur, 0.10);

			if (isInit) {
				var left = 0 + Math.floor(Math.random() * 90);
				dur = ((100 - left) / 100) * dur;
				$("#cloud" + cloudId).animate(
					{left: left + "%"},
					initDur * 0.9,
					"swing",
					function() {
						continueCloud(cloudId, dur);
					});
			}

			else {
				$("#cloud" + cloudId).animate({left: "100%"}, dur, "linear");
			}

			var delay = (plane.dur / 3) + (Math.random() * plane.dur / 3);
			//var delay = 3000;
			if (repeat) {
				if (isInit) {
					delay *= 0.5;
				}
				setTimeout(
					function() {
						generateCloud(planeIndex, false, true)
					},
					delay);
			}
		}

		var nextBeestId = 0;

		function generateHerds()
		{
			generateHerd(
				function()
				{
					return window.innerWidth + 500;
				},
				-500,
				4,
				0.7);
		}

		function generateHerd(
			beestSource,
			beestTarget,
			pixelsPerSecond,
			initialAdjust)
		{
			console.log("generateHerd { " +
				"beestSource: " + beestSource() + "; " +
				"beestTarget: " + beestTarget + "; " +
				"pixelsPerSecond: " + pixelsPerSecond + "; " +
				"initialAdjust: " + initialAdjust + "; " +
				"}");

			var herdSize = next(3, 9);
			var firstRight = beestTarget + (beestSource() * initialAdjust);
			var lastRight = firstRight;
			var dur = (lastRight - beestTarget) / pixelsPerSecond * 1000;

console.log(
	"herdSize: " + herdSize + "; " +
	"lastRight: " + lastRight + "; " +
	"dur: " + dur + "; ");

			for(var i = 0; i < herdSize; i++)
			{
				var beestId = nextBeestId++;
				var beestSprite = nextBeestId % 4;
				var nextLeft = 0;

				var beest = null;
				if (i == 0 || chance(0.70))
				{
					nextLeft = lastRight + next(5, 20);
					beest = makeBeestHtml(beestId, beestSprite, nextLeft);
					lastRight = nextLeft + 32;
				}
				else
				{
					nextLeft = lastRight + next(5, 10);
					beest = makeBeestBabyHtml(beestId, beestSprite, nextLeft);
					lastRight = nextLeft + 16;
				}

				var targetLeft = beestTarget + nextLeft - firstRight;

console.log(
	"beestTarget: " + beestTarget + "; " +
	"nextLeft: " + nextLeft + "; " +
	"firstRight: " + firstRight + "; " +
	"targetLeft: " + targetLeft + "; ");

				$("#beests").prepend(beest);
				$("#beest" + beestId).animate(
					{ left: targetLeft + "px" },
					dur * initialAdjust,
					"linear",
					function()
					{
					});
			}

			var nextHerdDelay = next(20000, dur);
console.log("nextHerdDelay: " + nextHerdDelay);

			setTimeout(
				function()
				{
					generateHerd(beestSource, beestTarget, pixelsPerSecond, 1.0);
				},
				nextHerdDelay);
		}

		function next(min, max)
		{
			if (min < max)
			{
				return min + Math.floor(Math.random() * (max - min + 1));
			}
			else
			{
				return max;
			}
		}

		function chance(percentage)
		{
			return Math.random() < percentage;
		}

		function makeBeestHtml(
			beestId,
			beestSprite,
			initialLeft)
		{
			return htmlEl(
				"img",
				htmlAtt("id", "beest" + beestId) +
				htmlAtt("src", "assets/beest_" + beestSprite + ".gif") +
				htmlAtt("width", 32) +
				htmlAtt("height", 20) +
				htmlAtt(
				"style",
					cssProp("position", "absolute") +
					cssProp("top", "200px") +
					cssProp("left", initialLeft)));
		}

		function makeBeestBabyHtml(
			beestId,
			beestSprite,
			initialLeft)
		{
			return htmlEl(
				"img",
				htmlAtt("id", "beest" + beestId) +
				htmlAtt("src", "assets/beest_baby_" + beestSprite + ".gif") +
				htmlAtt("width", 16) +
				htmlAtt("height", 10) +
				htmlAtt(
				"style",
					cssProp("position", "absolute") +
					cssProp("top", "210px") +
					cssProp("left", initialLeft)));
		}

		function continueCloud(
			cloudId,
			dur) {

			$("#cloud" + cloudId).animate({left: "100%"}, dur, "linear");

		}

		function htmlEl(name, attrs) {
			var close = !(
				name.toLowerCase() == "link" ||
				name.toLowerCase() == "img" ||
				name.toLowerCase() == "br"
			);
			
			return "<" + name + " " + attrs + (close ? "/>" : ">");
		}

		function htmlAtt(name, value) {
			return name + "=\"" + value + "\" ";
		}

		function cssProp(name, value) {
			return name + ": " + value + "; ";
		}

		function randomVariation(base, degree) {
			var dir = Math.floor(Math.random() * 2);
			var result = base;
			if (dir == 0) {
				result += result * degree;
			}
			else {
				result -= result * degree;
			}
			return result;
		}

