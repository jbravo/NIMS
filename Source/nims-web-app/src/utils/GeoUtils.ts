function checkPolylineInBox(bbox, latlngs) {
  var firstPoint = null;
  var secondPoint = null;
  // var line;
  var k = 0;
  for (; latlngs[k];) {
    var latlng = latlngs[k];
    k++;
    // latlngs.forEach(function (latlng) {
    if (firstPoint == null) {
      firstPoint = latlng
    } else {
      secondPoint = latlng;
      // line = L.Polyline([[firstPoint.lat, firstPoint.lng], [secondPoint.lat, secondPoint.lng]], {color: '#3333', opacity: 1});
      // if (lineRect(firstPoint.lat, firstPoint.lng, secondPoint.lat, secondPoint.lng, bbox._southWest.lat, bbox._southWest.lng, bbox._northEast.lat, bbox._northEast.lng)) {
      //     return true;
      // }
      if (lineIntersectsRect(firstPoint, secondPoint, bbox)) {
        return true;
      }
      firstPoint = secondPoint;
    }
    // });
  }
  return false;
}

function lineIntersectsRect(firstPoint1, secondPoint, bound) {
  if (bound.contains(firstPoint1)) {
    return true;
  }
  if (bound.contains(secondPoint)) {
    return true;
  }
  if (lineIntersectsLine(firstPoint1, secondPoint, {
    lat: bound._southWest.lat,
    lng: bound._southWest.lng
  }, {lat: bound._southWest.lat, lng: bound._northEast.lng})) {
    return true;
  }
  if (lineIntersectsLine(firstPoint1, secondPoint, {
    lat: bound._southWest.lat,
    lng: bound._northEast.lng
  }, {lat: bound._northEast.lat, lng: bound._northEast.lng})) {
    return true;
  }
  if (lineIntersectsLine(firstPoint1, secondPoint, {
    lat: bound._northEast.lat,
    lng: bound._northEast.lng
  }, {lat: bound._northEast.lat, lng: bound._southWest.lng})) {
    return true;
  }
  if (lineIntersectsLine(firstPoint1, secondPoint, {
    lat: bound._northEast.lat,
    lng: bound._southWest.lng
  }, {lat: bound._southWest.lat, lng: bound._southWest.lng})) {
    return true;
  }
  return false;
}

function lineIntersectsLine(l1p1, l1p2, l2p1, l2p2) {
  var q = (l1p1.lng - l2p1.lng) * (l2p2.lat - l2p1.lat) - (l1p1.lat - l2p1.lat) * (l2p2.lng - l2p1.lng);
  var d = (l1p2.lat - l1p1.lat) * (l2p2.lng - l2p1.lng) - (l1p2.lng - l1p1.lng) * (l2p2.lat - l2p1.lat);
  if (d == 0) {
    return false;
  }
  var r = q / d;
  q = (l1p1.lng - l2p1.lng) * (l1p2.lat - l1p1.lat) - (l1p1.lat - l2p1.lat) * (l1p2.lng - l1p1.lng);
  var s = q / d;
  if (0 > r) {
    return false;
  }
  if (r > 1) {
    return false;
  }
  if (0 > s) {
    return false;
  }
  if (s > 1) {
    return false;
  }
  return true;
}
