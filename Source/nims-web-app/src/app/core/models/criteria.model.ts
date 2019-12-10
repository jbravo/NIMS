import { Pagination } from './pagination.model';

export class CriteriaModel extends Pagination {
  criteriaParentId: number;
  criteriaCode: string;
  criteriaName: string;
  criteriaLevel: number;
  criteriaNumber: number;
  hasContent: number;
  status: number
}
