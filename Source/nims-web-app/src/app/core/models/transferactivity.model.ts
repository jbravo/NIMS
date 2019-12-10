import { Pagination } from './pagination.model';

export class transferactivityModel extends Pagination {
  organizationControllerId: number;
  statusLong: number;
  partyMemberId :number;
  transferType :number;
  transferDateFrom: string ;
  transferDateTo: string;
  status : string;
  isExistPartyMember:number;
  }
