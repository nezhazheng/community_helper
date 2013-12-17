 Ext.define('Mgr.model.Menu', {
     extend: 'Ext.data.Model',
     fields: ['title', 'widgetId']
 });

Ext.define('Mgr.view.Navigator', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mgrnavigator',
    layout: 'fit',
    title: '菜单',
    
    createView: function(){
        this.view = Ext.create('widget.dataview', {
            autoScroll: true,
            store: Ext.create('Ext.data.Store', {
            	model: 'Mgr.model.Menu',
                data: [
                       {
                    	   title : '用户列表',
                    	   widgetId: 'usersgrid',
                    	   id: 'users'
                       }, {
                    	   title : '类别商户列表',
                    	   widgetId: 'merchanttree',
                    	   id: 'merchanttree'
                       }, {
                    	   title : '商户列表',
                    	   widgetId: 'allmerchantsgrid',
                    	   id: 'allmerchants'
                       }, {
                    	   title : '用户实名认证',
                    	   widgetId: 'realnameauthgrid',
                    	   id: 'realnameauth'
                       }, {
                    	   title : '图片列表',
                    	   widgetId: 'imagegrid',
                    	   id: 'images'
                       }, {
                    	   title : '评论列表',
                    	   widgetId: 'feedbackgrid',
                    	   id: 'feedbacks'
                       }, {
                    	   title : '商户报错列表',
                    	   widgetId: 'merchanterrorreportgrid',
                    	   id: 'merchanterrorreport'
                       }
                ]
            }),
            selModel: {
                mode: 'SINGLE',
                listeners: {
                    scope: this,
                    selectionchange: this.onSelectionChange
                }
            },
            listeners: {
                scope: this
            },
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: '<tpl for="."><div class="feed-list-item">{title}</div></tpl>'
        });
        return this.view;
    },
    
    onSelectionChange: function(data) {
    	if(data.selected.items.length == 0){
    		return;	
    	}
    	Ext.getCmp('right-container').removeAll(false);
    	Ext.getCmp('right-container').add({xtype: data.selected.items[0].data.widgetId});
    },
    
    initComponent: function(){
    	Ext.apply(this, {
            items: this.createView()
        });
    	
    	this.callParent(arguments);
    }
})