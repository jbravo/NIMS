import { Component, OnInit, ViewChild, ElementRef, Input } from '@angular/core';
import * as $ from 'jquery';
import { FormControl } from '@angular/forms';
@Component({
  selector: 'menu-control',
  templateUrl: './menu-control.component.html',
  styleUrls: ['./menu-control.component.css']
})
export class menuControlComponent implements OnInit {
  @ViewChild('mainEmpTab') mainEmpTab: ElementRef;
  constructor() {

  }
  @Input()
  public property: FormControl;
  @Input()
  public viewParent: string; //0. quan ly can bo ;  1. Quan ly dang vien- thong tin chung t63 2. Quan ly Bao ve an ninh
  @Input()
  public action: FormControl;
  isOpenMenu:Boolean;
  ngOnInit() {
    this.getJquery();
    
  }
  ngAfterViewInit(){
  };
  /**
   * Lay cau hinh cac thuoc tinh
   */
  private mouseDownX = 0;
  private mouseDownY = 0;

  getLocationDown(event) {
    if(this.mainEmpTab.nativeElement.classList.contains('open')){
      this.isOpenMenu = true
    }else{
      this.isOpenMenu = false
    }
    
    console.log(this.mainEmpTab.nativeElement.classList);
    this.mouseDownX = event.screenX;
    this.mouseDownY = event.screenY;
  }
  opendialog(event) {
    if (event.screenX === this.mouseDownX && event.screenY === this.mouseDownY) {
      // if(!this.mainEmpTab.nativeElement.classList.contains('moving')){
        
        if(this.isOpenMenu){
          this.mainEmpTab.nativeElement.classList.remove('open');
          this.isOpenMenu = false
        }else{
          this.mainEmpTab.nativeElement.classList.add('open');
          this.isOpenMenu = true
        }
      
      // }else{
      // }
    }

  }
  caculatorLeft() {
    const buttonLeft = 0
  }
  getJquery() {
    
    var positionInfor = {};
    function caculatorLeft() {
      var buttonLeft = $('#mainEmpTabs').position().left - $('#nav-container').width(),
        contentWidth = $('#content').width() - 50,
        showWidth = $('#leftNavigator').width() + 20,
        buttonTop = $('#mainEmpTabs').position().top - 15,
//        contentHeight = $('#content').height() - 50,
        showHeight = $('#leftNavigator').height() + 20;
      $('#leftNavigator').css('margin-left', 'auto');
      $('#leftNavigator').css('margin-top', 'auto');
      $('#leftNavigator').css('bottom', '');
      if ($('#mainEmpTabs').hasClass('minTop') || $('#mainEmpTabs').hasClass('maxTop')) {
        if (buttonLeft + showWidth >= contentWidth ) {
          $('#leftNavigator').css('margin-left', 0 - buttonLeft - showWidth + contentWidth + 30);
        }
      }
      
      if ($('#mainEmpTabs').hasClass('minLeft') || $('#mainEmpTabs').hasClass('maxLeft')) {
        if (buttonTop <= showHeight) {
          $('#leftNavigator').css('bottom', 0 - showHeight + buttonTop + 50);
        } else {
          $('#leftNavigator').css('bottom', 0);
        }
      }
      if ($('#mainEmpTabs').hasClass('maxTop') && $('#mainEmpTabs').hasClass('maxLeft')) {
        $('#leftNavigator').css('bottom', 60);
      }
    }
    function caculateNewPosition() {
      var buttonLeft = $("#mainEmpTabs").position().left - $('#nav-container').width(),
        buttonTop = $("#mainEmpTabs").position().top - 15,
        contentWidth = $('#content').width(),
        contentHeight = $('#nav-container').height() - 150,
        percentLeft = (buttonLeft + 25) / contentWidth * 100,
        percentTop = (buttonTop + 25) / contentHeight * 100;

      var scope = {
        minLeft: $('#nav-container').width() + 10,
        maxLeft: $('#content').width() + $('#nav-container').width() - 75,
        minTop: 15,
        maxTop: $('#nav-container').height()  - 170,

      };

      var returnPosition = {
        left: $("#mainEmpTabs").position().left, top: $("#mainEmpTabs").position().top, classNamel: "", className2: ""
      };
      if (percentLeft <= 0) {
        returnPosition.left = scope.minLeft;
        returnPosition.classNamel = "minLeft";
      }
      if (percentTop <= 0) {
        returnPosition.top = scope.minTop;
        returnPosition.className2 = "minTop";
      }
      if (percentLeft >= 100) {
        returnPosition.left = scope.maxLeft;
        returnPosition.classNamel = "maxLeft";
      }
      if (percentTop >= 100) {
        returnPosition.top = scope.maxTop;
        returnPosition.className2 = "maxTop";
      }
      if (0 < percentLeft && percentLeft < 100 && 0 < percentTop && percentTop < 100) {
        var left = buttonLeft, top = buttonTop, right = contentWidth - buttonLeft, bottom = contentHeight - buttonTop;
        if (left == Math.min(left, top, right, bottom)) {
          returnPosition.left = scope.minLeft;
          returnPosition.classNamel = "minLeft";
        } else if (top == Math.min(left, top, right, bottom)) {
          returnPosition.top = scope.minTop;
          returnPosition.className2 = "minTop";
        } else if (right == Math.min(left, top, right, bottom)) {
          returnPosition.left = scope.maxLeft;
          returnPosition.classNamel = "maxLeft";
        } else {
          returnPosition.top = scope.maxTop;
          returnPosition.classNamel = "maxTop";
        }
      }
      return returnPosition;
    }
    function initDrag(e) {
      document.addEventListener('mousemove', doDrag, false);
      document.addEventListener('mouseup', stopDrag, false);
      // hideDialogOverlay();
      // $("#leftNavigator").fadeOut()

    }
    function doDrag(e) {
      $("#mainEmpTabs").css("left", e.clientX - 25);
      $("#mainEmpTabs").css("top", e.clientY - 25);
      $("#toggleTabsContent").addClass('moving');
      $("#mainEmpTabs").removeClass('open');
    }
    function stopDrag(e) {
      document.removeEventListener('mousemove', doDrag, false);
      document.removeEventListener('mouseup', stopDrag, false);
      //setTimeout(function() O("#toggLeTobsContent").removeCLassrmoving9;1, 100); 
      setTimeout(function () {
        $("#toggleTabsContent").removeClass('moving');
        var returnPosition = caculateNewPosition();
        gotoPosition(returnPosition, 500);
      }, 100);
    }
    function gotoPosition(returnPosition, time) {
      $("#mainEmpTabs").animate(returnPosition, time, function () {
        caculatorLeft();
      });
      $("#mainEmpTabs").removeClass('minLeft minTop maxLeft maxTop');
      $("#mainEmpTabs").addClass(returnPosition.classNamel);
      $("#mainEmpTabs").addClass(returnPosition.className2);
      // localStorage.setItem("positionNavigator", JSON.stringify(returnPosition));
    }
    if (typeof localStorage.getItem("positionNavigator") == 'string') {
      try {
        gotoPosition(JSON.parse(localStorage.getItem("positionNavigator")), 0);
        $('#mainEmpTabs').show();
      } catch (e) {
        $('#mainEmpTabs').show();
      }
    } else {
      $('#mainEmpTabs').show();
    }

    $("#toggleTabsContent").on("mousedown", initDrag);
    // function changeAction() {
    //   if ($('#mainEmpTabs').hasClass('open')) {
    //     $('#mainEmpTabs').addClass('open');
    //     return;
    //   } else {
    //     $('#mainEmpTabs').removeClass('open');
    //   }
    // }

    $(window).resize(function () {
      stopDrag(null);
    }
    );
  }
}
