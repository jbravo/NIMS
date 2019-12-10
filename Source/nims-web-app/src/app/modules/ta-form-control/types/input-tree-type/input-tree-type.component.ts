import {Component, OnInit} from '@angular/core';
import {FieldType} from '@ngx-formly/core';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';
import {TreeNode} from 'primeng/api';

@Component({
  selector: 'input-tree-type',
  templateUrl: './input-tree-type.component.html',
  styleUrls: ['./input-tree-type.component.scss']
})
export class InputTreeTypeComponent extends FieldType implements OnInit {

  tree: TreeNode[];

  selectedFiles: TreeNode[];
  selectedNode: any;
  loading: any;

  constructor(private suggestService: SuggestionStationService) {
    super();
  }

  ngOnInit() {
    this.loading = true;
    this.suggestService.getDeptsByParentId().subscribe(res => {
      this.tree = res.body.data;
      this.loading = false;
    }, error1 => {
      this.loading = false;
    });
  }

  loadNode(event) {
    if (event.node) {
      this.loading = true;
      this.suggestService.getDeptsByParentId(event.node.data.deptId).subscribe(res => {
        event.node.children = res.body.data;
        this.loading = false;
      }, error1 => {
        this.loading = false;
      });
    }
  }

  selectNode(event: any) {
    if (event.node) {
      this.to.inputModel = event.node.data.deptName;
      this.formControl.setValue(event.node.data.deptId);
    }
  }
}
