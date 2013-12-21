Ext.define('Mgr.view.ModifyCategoryWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.modifycategorywindow',
	id: 'modifycategorywindow',
    title: '添加类别',
    height: 400,
    width: 400,
    layout: 'fit',
    items: {
        xtype: 'form',
        url: '/mgr/category/modify',
        method: 'GET',
        layout: 'anchor',
        disableCaching:false,
        defaults: {
            anchor: '100%'
        },
        defaultType: 'textfield',
        items: [{
            fieldLabel: 'id',
            name: 'id',
            hidden: true
        },{
            fieldLabel: 'name',
            name: 'name',
            allowBlank: false
        },{
            fieldLabel: 'order',
            name: 'order',
            allowBlank: false
        },{
            fieldLabel: '图片ID',
            name: 'iconId',
            allowBlank: false
        },{
            fieldLabel: 'categoryId',
            name: 'categoryId',
            allowBlank: false
        }],
        buttons: [{
            text: 'Reset',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }, {
            text: 'Submit',
            formBind: true,
            disabled: true,
            handler: function() {
                var form = this.up('form').getForm();
                if (form.isValid()) {
                    Ext.Ajax.request({
                    	url: '/mgr/category/modify',
                        method: 'POST',
                        params: form.getValues(),
                    	disableCaching:false,
                        success: function(form, action) {
                           Ext.getCmp('merchanttree').store.load();
                           Ext.getCmp('modifycategorywindow').close();
                        },
                        failure: function(form, action) {
                            Ext.Msg.alert('Failed', action);
                        }
                    });
                }
            }
        }],
        listeners: {
	        afterrender: function(form) {
	        	form.loadRecord(Ext.getCmp('modifycategorywindow').data);
	        }
        }
    }
});