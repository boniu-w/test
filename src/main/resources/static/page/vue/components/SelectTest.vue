<template>
  <a-select default-value="lucy" style="width: 200px" @change="handleChange">
    <a-select-opt-group>
      <span slot="label"><a-icon type="user" />Manager</span>
      <a-select-option value="jack">
        Jack
      </a-select-option>
      <a-select-option value="lucy">
        Lucy
      </a-select-option>
    </a-select-opt-group>
    <a-select-opt-group label="Engineer">
      <a-select-option value="Yiminghe">
        yiminghe
      </a-select-option>
    </a-select-opt-group>
  </a-select>
</template>
<script>
export default {
  methods: {
    handleChange(value) {
      console.log(`selected ${value}`);
    },
  },
};
</script>
