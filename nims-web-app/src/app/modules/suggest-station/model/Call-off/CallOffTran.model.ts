import {NewDesignOpticalLaneModel} from '@app/modules/suggest-station/model/Call-off/newDesignOpticalLane.model';
import {OpticalLaneAvailableModel} from '@app/modules/suggest-station/model/Call-off/opticalLaneAvailable.model';

export class CallOffTranModel {
  callOffType: number; // loại call of
  transInterface: number; // giao diện truyền dẫn
  note: string; // ghi chú 500
  designer: string; // người thiết kế
  newDesignOpticalLaneModel: NewDesignOpticalLaneModel[];
  opticalLaneAvailableModel: OpticalLaneAvailableModel[];
}
