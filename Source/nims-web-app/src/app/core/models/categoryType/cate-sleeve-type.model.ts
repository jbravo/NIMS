import { Pagination } from './../pagination.model';

export class CatSleeveType extends Pagination {
    sleeveTypeId: number;
    sleeveTypeCode: string;
    type: number;
    capacity: number;
    note: string;
    createTime: Date;
    updateTime: Date;
    rowStatus: number;
}