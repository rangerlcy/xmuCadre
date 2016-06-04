function deletePhoto(thiz){	
	if($(thiz).parent().parent().children().length > 1){
		$(thiz).parent().remove();
	}
}
function addPhoto(thiz){
	var div='<div class="photo"><input type="file" name="photo"/><span onclick="deletePhoto(this)">移除</span><span onclick="addPhoto(this)">添加</span></div>';
	$(thiz).parent().after(div);
}
function deleteDismiss(thiz){
	if($(thiz).parent().parent().parent().children(".extenceInfo").length > 1){
		$(thiz).parent().parent().remove();
	}
	changeDismiss();
}
