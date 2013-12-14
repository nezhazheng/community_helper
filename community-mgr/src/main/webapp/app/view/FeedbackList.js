var feedbackStore = Ext.create('Ext.data.Store',{
    requires: 'Mgr.model.Feedback',    
    model: 'Mgr.model.Feedback',
    pageSize: 20,
    proxy: {
         type: 'ajax',
         url: '/mgr/merchant/feedback',
         actionMethods: {
             create: 'POST',
             destroy: 'POST',
             read: 'GET',
             update: 'POST'
         },
         noCache: false,
         autoAppendParams: true,
         reader: {
             type: 'json',
             totalProperty: 'totalResult',
             root: 'list'
         }
    },
    autoLoad: true
});

Ext.define('Mgr.view.FeedbackList', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.feedbackgrid',
	id: 'feedbacks',
	requires: [
	           'Ext.selection.CellModel',
	           'Ext.grid.*',
	           'Ext.form.field.ComboBox',
	           'Ext.data.*',
	           'Ext.util.*',
	           'Ext.form.*',
	           'Ext.PagingToolbar'
	       ],
	frame: true,
    columns: [{
        text: '留言编号',
        dataIndex: 'id',
        width: 150
    }, {
        header: '商户名称',
        dataIndex: 'merchantName',
        width: 150,
        sortable: true
    }, {
        text: '留言内容',
        dataIndex: 'message',
        width: 150
    }, {
    	text: '评分',
    	dataIndex: 'score',
    	width: 150
    }, {
        text: '手机号',
        dataIndex: 'phonenum',
        width: 150
    }, {
    	text: '留言时间',
    	dataIndex: 'createDate',
    	width: 150
    }],
    tbar: [{
		xtype: 'button',
	    text: '删除评论',
	    scope: this,
	    handler: function(){
        	var data = Ext.getCmp('feedbacks').getSelectionModel().getSelection()[0].data;
        	Ext.Ajax.request({
        	    url: '/mgr/merchant/feedback/delete',
        	    method: 'GET',
        	    autoAppendParams: true,
        	    disableCaching:false,
        	    params: {
        	    	id: data.id
        	    },
        	    success: function(response){
        	        Ext.getCmp('feedbacks').store.load();
        	    }
        	});
        }
    }],
    store: feedbackStore,
    // paging bar on the bottom
    bbar: Ext.create('Ext.PagingToolbar', {
        store: feedbackStore,
        displayInfo: true,
        displayMsg: 'Displaying feedbacks {0} - {1} of {2}',
        emptyMsg: "No feedbacks to display"
    }),
    title: '所有评论'
});