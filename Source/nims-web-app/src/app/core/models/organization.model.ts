import { Pagination } from './pagination.model';

export class organizationModel extends Pagination {
    parentId: number;
    organizationCode: string;
    organizationId: number;
    activityDateFrom: string ;
    activityDateTo: string;
    
  }
