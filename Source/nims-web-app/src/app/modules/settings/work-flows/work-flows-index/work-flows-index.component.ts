import { Component, OnInit } from '@angular/core';
import { WfMenuMappingService } from '../work-flows.service';

@Component({
  selector: 'work-flows-index',
  templateUrl: './work-flows-index.component.html',
  styles: []
})
export class WorkFlowsIndexComponent implements OnInit {
  data: any = [];
  constructor(private wfMenuMappingService: WfMenuMappingService) { }

  ngOnInit() {
    this.wfMenuMappingService.findNodes().subscribe(res => {
      console.log(res);
      this.data = res.data;
      this.buildTree();
    });
  }
  buildTree(): void {
    const dataMap = this.data.reduce((m, d) => {
      m[d.nodeId] = Object.assign({}, d);
      return m;
    }, {});
    const trees = this.data.filter(d => {
      if (d.parentId !== null) { // assign child to its parent
        const parentNode = dataMap[d.parentId];
        if (parentNode['children'] === undefined || parentNode['children'] === null ) {
          parentNode['children'] = [];
        }
        parentNode.children.push(dataMap[d.nodeId]);
        return false;
      }
      return true; // root node, do nothing
    }).map(d => dataMap[d.nodeId]);
  }
}
