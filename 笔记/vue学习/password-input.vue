<template>

	<div id="app">
		<div class="password-out">
			<div class="password-text">
				请输入一次性密码
				<font class="font-she" :value="time"> {{time}}秒</font>
			</div>

			<div class="password-input" @click="getfoucs($event)">

				<input type="text" :name="passwordinputname" id="password-number" value="" class="password-number " ref="password" v-model="msgnumber" @keyup="getinputval($event)" />

				<p class="password-input-p">

					<span class="password-input-span border-left" v-if="inputpasswordlength>='1'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=1}" ></p>
						<span v-if="securitycode=='Y'">
							{{msgnumberarr[0]}}
						</span>

					</span>
					<span class="password-input-span" v-if="inputpasswordlength>='2'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=2}" ></p>
							<span v-if="securitycode=='Y'">
							{{msgnumberarr[1]}}
						</span>
					</span>
					<span class="password-input-span" v-if="inputpasswordlength>='3'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=3}"></p>
							<span v-if="securitycode=='Y'">
							{{msgnumberarr[2]}}
						</span>
					</span>
					<span class="password-input-span" v-if="inputpasswordlength>='4'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=4}"></p>
							<span v-if="securitycode=='Y'">
							{{msgnumberarr[3]}}
						</span>
					</span>
					<span class="password-input-span" v-if="inputpasswordlength>='5'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=5}"></p>
							<span v-if="securitycode=='Y'">
							{{msgnumberarr[4]}}
						</span>
					</span>
					<span class="password-input-span " v-if="inputpasswordlength>='6'">
						<p class="password-input-p-p" :class="{showp:lenghtnumeb>=6}"></p>
							<span v-if="securitycode=='Y'">
							{{msgnumberarr[5]}}
						</span>
					</span>
				</p>
			</div>
		</div>

	</div>

</template>

<script>
	export default {
		name: 'passwordinput',
		pageTitle: 'passwordinput组件',
		props: {
			securitycode: String, //控制是否可以输入  Y  可以输入  N 不可以输入
			default: "Y",
			passwordinputname: String,
			default: "password01",
			inputpasswordlength: String, //显示 多少个密码输入框
			default: "6",
			passwordhide: String, // 控制隐藏和显示密码   Y 隐藏密码 N显示密码 
			default: "Y",
			numberorString:String,  //控制 是否可以输入 数字 或字母  number  表示 可以输入数字 String 表示可以输入字母     
			default: "number",
		},
		components: {

		},

		data() {
			return {

				suoyin: '012',
				msgnumber: '', //input 输入的值
				lenghtnumeb: '', //input 输入的值的长度
				msgnumberarr: [],
				time: 99
			}
		},
		mounted() { //加载完了之后 可以执行的方法
			//加载完成后 input 获取到方法
			this.$refs.password.focus();
		},
		created() { //创建的时候执行的方法
			console.log(this.$refs);
			this.daojishi();
		},
		methods: {

			getinputval(e) {

		

				var _this = this;

				console.log(_this.$refs.password.value);

				console.log(_this.msgnumber);

				//判断 控制是否可以输入

				if(_this.securitycode == 'N') {
					// 等于 N  表示不能输入  数据清零
					_this.msgnumber = "";
				}


				var msgnumberarr ;

				if (_this.numberorString== 'number') { //表示只能输入数字
					
					//替换非数字为空

			msgnumberarr = _this.msgnumber.toString().replace(/[^0-9]/ig, '');
				
					
				} else{//只能输入字符 Abc
					// 非 a-zA-Z 全部替换为空
				 msgnumberarr = this.msgnumber.toString().replace(/[^a-zA-Z]/ig, '');

				}

				
				
				//把优化后的 字符 赋值给 input 框
				_this.msgnumber = msgnumberarr;

				console.log(msgnumberarr);
				// 把 优化后的字符串 转成数组
				_this.msgnumberarr = msgnumberarr.split("");

				if(_this.passwordhide == 'N') { //显示输入的所有密码

					//把这个原点 数变成 -1  就密码可见了 
					_this.lenghtnumeb = -1

				} else { //输入一个密码 就隐藏一个密码

					// 控制 输入框的原点数  输入的字符 或数字的长度
					_this.lenghtnumeb = msgnumberarr.length;
				}

				console.log(_this.lenghtnumeb);

				//当输入的字符框长度与 限定的密码框位数想同时即 传递输入的数据到后台
				if(msgnumberarr.length == _this.inputpasswordlength) {

					console.log("输入完毕")
					this.$emit('tomsgnumberval', this.msgnumber)
				}

				if(msgnumberarr.length > _this.inputpasswordlength) { //防止输入的数据多了 多提交数据

					console.log("输完哦了");
					var substr = _this.msgnumber.substring(0, msgnumberarr.length - 1)
					console.log("------------------")
					console.log(substr);

					_this.msgnumber = substr;
				}

			},
			getfoucs(e) {

				//				console.log(this.$refs.password);

				this.$refs.password.focus();

				//				console.log(this.msgnumber);

			},

			toDetail() {

			},

			cancel() {
				console.log("oncancel");
			},
			confirm() {
				console.log("onconfirm");
			},
			daojishi() {
				//这一步必须加	
				var _this = this;

				var t = _this.time;
				//				console.log(t)

				var c = setInterval(function() {

					t--;

					_this.time = t;
					//					console.log(t);

					if(t <= 0) {
						clearInterval(c);
					}
				}, 1000);
			},

			inclick() {
				console.log("inclick");
			}

		}
	}
</script>

<style>
	#app {
		height: auto;
	}
	
	.password-out {
		width: 100%;
		height: 6.5625rem;
		background-color: #B9B1B1;
		margin: 0 auto;
		padding: 0 0.9375rem;
	}
	
	.password-text {
		width: 100%;
		height: 1.4375rem;
		text-align: left;
		line-height: 1.4375rem;
		/*position: relative;*/
		font-size: 12px;
	}
	
	.password-input {
		width: 100%;
		height: 3.25rem;
		margin-top: 0.3125rem;
	}
	
	.password-input-p {
		width: 100%;
		height: 3.25rem;
		display: flex;
		justify-content: center;
	}
	
	.password-input-span {
		display: inline-block;
		width: 3.125rem;
		height: 2.75rem;
		border: solid black 1px;
		margin-top: 0.125rem;
		float: left;
		/*border-collapse: collapse;*/
		border-left: 0!important;
		background-color: #fff;
		text-align: center;
		line-height: 2.75rem;
		position: relative;
	}
	
	.border-left {
		border-left: 1px solid black!important;
	}
	
	.password-input-p-p {
		display: none;
		width: 0.625rem;
		height: 0.625rem;
		border-radius: 50%;
		background-color: #000000;
		position: absolute;
		left: 50%;
		top: 50%;
		margin-top: -5px;
		margin-left: -5px;
	}
	
	.password-number {
		position: absolute;
		left: -1000px;
	}
	/*.password-input-span:after {
		content: "<p ></p>";

	}*/
	
	.showp {
		display: block;
	}
	
	.font-she {
		color: red;
		height: 100%;
		width: 50px;
		display: block;
		float: right;
		padding-right: 10px;
		font-size: 8px;
	}
</style>