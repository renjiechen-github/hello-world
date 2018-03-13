var create_list = {
		init:function()
		{			
			njyc.phone.showProgress('');
			create_list.loadSelect();
			create_list.getHouseList();
			$('#rightdiv').click(function()
			{
				$(this).find('img').toggleClass('rot2');
				$('#hide1').toggle();
			});
			$('#taskstate,#taskname,#taskname1').change(function()
			{
				$('#rightdiv').find('img').toggleClass('rot2');
				$('#hide1').toggle();
				refreshlList();
//				$('.body').html('');
//				isLoading = false;
//				page_num = 0;
//				house_list.getHouseList();
			});
			$('.divflex2').click(function(){
				
			});
			$('#status').change(function()
			{
				$('.body').html('');
				isLoading = false;
				page_num = 0;
				if($(this).val() == '11' || $(this).val() == '12')
				{
					$('.divflex2').hide();
					$('.divflex3').show();
					create_list.getRankAgreementList();
				}
				else
				{
					$('.divflex2').show();
					$('.divflex3').hide();
					create_list.getHouseList();
				}
			});
			$('#sreachBut').click(function()
			{
				refreshlList();
			});		
		},
};