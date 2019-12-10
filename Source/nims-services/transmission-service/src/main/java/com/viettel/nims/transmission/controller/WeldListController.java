package com.viettel.nims.transmission.controller;


import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weldList")
public class WeldListController {

  JSONObject jsonObject = new JSONObject();

//  @Autowired
//  WeldListService weldListService;
//
//  @PostMapping(value = "/search/basic")
//  public ResponseEntity<?> findBasicsWeld() {
//    return weldListService.findBasicsWeld();
//  }
//
//  @PostMapping("/delete")
//  public @ResponseBody
//  JSONObject deleteMultipe(@RequestBody List<WeldListBO> weldListBO) {
//    JSONObject data = weldListService.deleteMultipe(weldListBO);
////    if (infraStationService.delete(id) > 0) {
//    jsonObject = buildResultJson(1, "success", data);
////      return new ResponseEntity<>(HttpStatus.OK);
////    }
//    return jsonObject;
//  }

  protected JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }

}
