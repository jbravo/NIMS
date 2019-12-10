import { Pagination } from './../pagination.model';

export class CatePillarType extends Pagination{
    pillarTypeId: number;
    pillarTypeCode: string;
    note: string;
    height: number;
    createTime: Date;
    updateTime: Date;
    rowStatus: number;
}